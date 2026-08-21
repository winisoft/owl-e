package com.wini.owle.query

import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.entity.environs.DistributionPackages
import com.wini.owle.entity.environs.PackagePythonRequirements
import com.wini.owle.entity.environs.Versions
import com.wini.owle.environs.VersionSpec
import com.wini.owle.environs.VersionSpec.Clause
import com.wini.owle.environs.VersionSpec.VersionComponents
import com.wini.owle.repo.ExposedTransactionExecutor
import com.wini.owle.repo.TransactionExecutor
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.JoinType
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll


/**
 * Which versions of a distribution exist, and which of them can run.
 *
 * **These do not go through `OwleRepo`, and cannot.** Its only filter is
 * `getWhere(predicate: (T) -> Boolean)`, implemented as `entityClass.all().filter(predicate)` — a
 * full table scan into memory. That is survivable for the 268 rows in `versions` and ruinous for
 * the 596,684 in `distributions` or the millions in `packages`. A domain query needs a real `WHERE`,
 * so these use the Exposed DSL directly.
 *
 * The generic CRUD layer keeps its job: fetch one row, write one row. Questions about the domain
 * live here.
 */
class VersionQueries(
    private val transactions: TransactionExecutor = ExposedTransactionExecutor
) {

    /** A version row, reduced to what ordering and specifier evaluation need. */
    data class VersionRow(val id: ULong, val components: VersionComponents, val hasOverflow: Boolean)

    /**
     * `packages` joined to `versions`.
     *
     * Stated explicitly rather than with `innerJoin`: the reference lives on a `CompositeIdTable`
     * as one of its key columns, and Exposed's inference does not find a matching key pair there —
     * it fails at execution with "no matching primary key/foreign key pair", not at compile time.
     */
    private val packagesWithVersion
        get() = DistributionPackages.join(
            Versions, JoinType.INNER,
            onColumn = DistributionPackages.version, otherColumn = Versions.id
        )

    /**
     * `package_python_requirements` joined to `versions`.
     *
     * The column references `python_versions`, whose own id *is* a `versions` id — a marker table
     * with no columns of its own — so the join goes straight to `versions` and skips a hop that
     * would add nothing.
     */
    private val requirementsWithVersion
        get() = PackagePythonRequirements.join(
            Versions, JoinType.INNER,
            onColumn = PackagePythonRequirements.pythonVersion, otherColumn = Versions.id
        )

    private fun ResultRow.toVersionRow() = VersionRow(
        id = this[Versions.id].value,
        components = VersionComponents(
            epoch = this[Versions.epoch],
            major = this[Versions.major],
            minor = this[Versions.minor],
            micro = this[Versions.micro],
            phase = this[Versions.phase],
            phaseNumber = this[Versions.phaseNumber],
            postNumber = this[Versions.postNumber],
            devNumber = this[Versions.devNumber],
            local = this[Versions.local]
        ),
        hasOverflow = this[Versions.hasOverflow]
    )

    // -----------------------------------------------------------------------------------------
    // versions of a distribution
    // -----------------------------------------------------------------------------------------

    /**
     * Every version of [distribution] that has been published, newest first.
     *
     * Overflowed versions are **included but flagged**. Excluding them would quietly drop real
     * releases; including them without the flag would let a caller compare numeric columns that
     * are known to be wrong. See `docs/TYPE-MODEL-REVIEW.md` §2.
     */
    fun versionsOf(distribution: String): List<VersionRow> = transactions.run {
        packagesWithVersion
            .select(
                Versions.id, Versions.epoch, Versions.major, Versions.minor, Versions.micro,
                Versions.phase, Versions.phaseNumber, Versions.postNumber, Versions.devNumber,
                Versions.local, Versions.hasOverflow
            )
            .where { DistributionPackages.distribution eq distribution }
            .map { it.toVersionRow() }
            .sortedWith(compareByDescending(VersionSpec.ORDERING) { it.components })
    }

    /**
     * Versions of [distribution] between two bounds.
     *
     * Bounded in SQL where it is cheap — epoch and the release components narrow the candidate set
     * with an index — and settled in Kotlin where it is correct, because pre-release ordering is
     * not expressible as a column comparison. A row whose release matches a bound exactly still has
     * to be ranked by its phase, post and dev numbers, and `1.0rc1 < 1.0` is not something SQL can
     * be told without encoding the whole rule as a `CASE`.
     */
    fun versionsOfBetween(
        distribution: String,
        lower: VersionComponents? = null,
        upper: VersionComponents? = null,
        includeLower: Boolean = true,
        includeUpper: Boolean = false
    ): List<VersionRow> = transactions.run {
        // The coarse bound is expressed in SQL so an index can be used; the exact one is settled
        // in Kotlin below, because pre-release ordering is not a column comparison — `1.0rc1 < 1.0`
        // cannot be stated to SQL without encoding the whole PEP 440 rule as a CASE.
        packagesWithVersion
            .selectAll()
            .where {
                var predicate = DistributionPackages.distribution eq distribution
                lower?.let { predicate = predicate and (Versions.major greaterEq it.major) }
                upper?.let { predicate = predicate and (Versions.major lessEq it.major) }
                predicate
            }
            .map { it.toVersionRow() }
            .filter { row ->
                val aboveLower = lower?.let {
                    val c = VersionSpec.compare(row.components, it)
                    if (includeLower) c >= 0 else c > 0
                } ?: true
                val belowUpper = upper?.let {
                    val c = VersionSpec.compare(row.components, it)
                    if (includeUpper) c <= 0 else c < 0
                } ?: true
                aboveLower && belowUpper
            }
            .sortedWith(compareByDescending(VersionSpec.ORDERING) { it.components })
    }

    // -----------------------------------------------------------------------------------------
    // Requires-Python
    // -----------------------------------------------------------------------------------------

    /**
     * The Requires-Python clauses declared by one package.
     *
     * Several rows form one conjunction — `>=3.9,<3.13` is two — so the result is the whole clause
     * set for that (distribution, version) pair.
     */
    fun requiresPythonFor(distribution: String, packageVersionId: ULong): List<Clause> =
        transactions.run {
            requirementsWithVersion
                .selectAll()
                .where {
                    (PackagePythonRequirements.distribution eq distribution) and
                        (PackagePythonRequirements.packageVersion eq packageVersionId)
                }
                .map { row ->
                    Clause(
                        operator = row[PackagePythonRequirements.operator].value,
                        wildcard = row[PackagePythonRequirements.wildcard].value,
                        target = VersionComponents(
                            epoch = row[Versions.epoch],
                            major = row[Versions.major],
                            minor = row[Versions.minor],
                            micro = row[Versions.micro],
                            phase = row[Versions.phase],
                            phaseNumber = row[Versions.phaseNumber]
                        )
                    )
                }
        }

    /**
     * Versions of [distribution] that will run on [python], newest first.
     *
     * The question the environment view asks first, and the reason the `versions` table is stored
     * decomposed: a package is a candidate when every one of its Requires-Python clauses admits the
     * interpreter, and that is a comparison of numbers rather than a parse of strings.
     *
     * A package declaring no Requires-Python is treated as admitting everything, which is what the
     * absence of the field means on an index.
     */
    fun versionsOfFor(distribution: String, python: VersionComponents): List<VersionRow> =
        transactions.run {
            val candidates = versionsOf(distribution)
            val clausesByVersion = clausesFor(distribution)
            candidates.filter { candidate ->
                val clauses = clausesByVersion[candidate.id].orEmpty()
                clauses.isEmpty() || VersionSpec.satisfiesAll(python, clauses)
            }
        }

    /**
     * The newest version of [distribution] that runs on [python], or null when none does.
     *
     * Overflowed versions are skipped here, unlike in [versionsOf]: picking a "newest" means
     * trusting the ordering, and an overflowed row is precisely the one whose numeric columns
     * cannot be trusted.
     */
    fun newestFor(distribution: String, python: VersionComponents): VersionRow? =
        versionsOfFor(distribution, python).firstOrNull { !it.hasOverflow }

    /**
     * Which of [pythons] can run [distribution] at [packageVersionId].
     *
     * This is what draws one band on the environment view's constraint chart.
     */
    fun pythonsAdmittedBy(
        distribution: String,
        packageVersionId: ULong,
        pythons: List<VersionComponents>
    ): List<VersionComponents> {
        val clauses = requiresPythonFor(distribution, packageVersionId)
        if (clauses.isEmpty()) return pythons
        return pythons.filter { VersionSpec.satisfiesAll(it, clauses) }
    }

    /**
     * Requires-Python for every version of one distribution, in a single round trip.
     *
     * Keyed by distribution rather than by an `IN` over version ids: one distribution's packages
     * are a small set, and the alternative needs the ids wrapped as `EntityID` because
     * `package_id` is an `entityId()` column — more ceremony than the narrowing is worth.
     */
    private fun clausesFor(distribution: String): Map<ULong, List<Clause>> {
        return requirementsWithVersion
            .selectAll()
            .where { PackagePythonRequirements.distribution eq distribution }
            .groupBy({ it[PackagePythonRequirements.packageVersion].value }) { row ->
                Clause(
                    operator = row[PackagePythonRequirements.operator].value,
                    wildcard = row[PackagePythonRequirements.wildcard].value,
                    target = VersionComponents(
                        epoch = row[Versions.epoch],
                        major = row[Versions.major],
                        minor = row[Versions.minor],
                        micro = row[Versions.micro],
                        phase = row[Versions.phase],
                        phaseNumber = row[Versions.phaseNumber]
                    )
                )
            }
    }

    /** The Python releases, newest first — the axis the constraint chart is drawn against. */
    fun pythonReleases(stableOnly: Boolean = true): List<VersionRow> = transactions.run {
        Versions
            .selectAll()
            .orderBy(
                Versions.major to SortOrder.DESC,
                Versions.minor to SortOrder.DESC,
                Versions.micro to SortOrder.DESC
            )
            .map { it.toVersionRow() }
            .filter { !stableOnly || it.components.phase == PrereleasePhase.FINAL }
    }
}
