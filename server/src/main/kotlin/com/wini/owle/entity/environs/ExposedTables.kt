package com.wini.owle.entity.environs

import com.wini.owle.entity.OwleCompositeIdTable

import com.wini.owle.entity.dbEnum

import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.entity.StringIdTable
import com.wini.owle.dto.environs.version.enums.OverflowViolation
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.CompositeIdTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.core.dao.id.ULongIdTable
import org.jetbrains.exposed.v1.core.or


object Distributions : StringIdTable("distributions")


object DistributionPackages : OwleCompositeIdTable("packages") {
    val version = keyReference("version_id", Versions)
    val distribution = keyReference("distribution", Distributions)
    val versionString = varchar("version_str", 255)
    val summary = varchar("summary", 4096).nullable()
    val keywords = text("keywords").nullable()
    val description = text("description").nullable()
    val descriptionContentType = varchar("description_content_type", 1024).nullable()
    val license = varchar("license", 2048).nullable()
    val homePage = varchar("home_page", 255).nullable()
    val projectUrl = varchar("project_url", 255).nullable()
    val downloadUrl = varchar("download_url", 255).nullable()
    val docsUrl = varchar("docs_url", 255).nullable()
    val yanked = bool("yanked").nullable()
    val lastSerial = uinteger("last_serial").nullable()

    override val primaryKey = PrimaryKey(version, distribution)
}


object PackageExtras : OwleCompositeIdTable("package_extras") {
    val distribution = keyReference("distribution", Distributions)

    /**
     * The version half of the package key. Deliberately *not* a reference to `versions`: the
     * database has no such constraint. A package is identified by (distribution, version)
     * together, and that pair is what the compound foreign key below enforces.
     */
    val packageVersion = ulong("package_id").entityId()
    val name = varchar("name", 255).entityId()
    val description = varchar("description", 4096).nullable()
    override val primaryKey = PrimaryKey(distribution, packageVersion, name)

    init {
        foreignKey(
            packageVersion to DistributionPackages.version,
            distribution to DistributionPackages.distribution,
            name = "package_extras_fk_pkg"
        )
    }
}


object PackageDistRequirements : ULongIdTable("package_dist_requirements") {
    val dependentDist = reference("dependent_dist", Distributions)
    val dependentVersion = ulong("dependent_package_id")
    val requiredDist = reference("required_dist", Distributions)
    val requiredVersion = ulong("required_package_id").nullable()
    val requiredExtraName = varchar("required_extra_name", 255).nullable()
    val operator = dbEnum<VersionOperator>("operator", 16)
    val wildcardSegment = dbEnum<WildcardSegment>("wildcard_segment", 8)
    val raw = varchar("raw", 1024).nullable()

    init {
        foreignKey(
            dependentDist to DistributionPackages.distribution,
            dependentVersion to DistributionPackages.version,
            name = "package_dist_requirements_ibfk_1"
        )
        foreignKey(
            requiredDist to DistributionPackages.distribution,
            requiredVersion to DistributionPackages.version,
            name = "fk_required_package"
        )
    }
}


object PackagePythonRequirements : OwleCompositeIdTable("package_python_requirements") {
    val distribution = keyReference("distribution", Distributions)

    /**
     * The version half of the package key. Deliberately *not* a reference to `versions`: the
     * database has no such constraint. A package is identified by (distribution, version)
     * together, and that pair is what the compound foreign key below enforces.
     */
    val packageVersion = ulong("package_id").entityId()
    val pythonVersion = keyReference("version_id", PythonVersions)

    /**
     * `operator` and `wildcard` are key columns, so they are registered as such: widening the
     * primary key to the whole clause only works if Exposed knows the widened set. `reference()`
     * gives a key part the right *type* without registering it, which is why [pythonVersion] is a
     * [keyReference] and these two take [asKey].
     */
    val operator = dbEnum<VersionOperator>("operator", 16).asKey()
    val wildcard = dbEnum<WildcardSegment>("wildcard", 8).default(WildcardSegment.NONE).asKey()
    /**
     * The whole clause, not just the package.
     *
     * A Requires-Python such as `>=3.9,<3.13` is two rows, and keying on (package, distribution)
     * alone allowed only one - so every bounded range collapsed to whichever clause was written
     * first. 22% of packages declare more than one.
     */
    override val primaryKey = PrimaryKey(packageVersion, distribution, pythonVersion, operator, wildcard)

    init {
        foreignKey(
            packageVersion to DistributionPackages.version,
            distribution to DistributionPackages.distribution,
            name = "ppr_fk_pkg"
        )
    }
}


/**
 * Maps 1:1 onto Versions.id — the `id` column is not its own auto-increment; it reuses the parent Versions PK.
 */
object PythonVersions : IdTable<ULong>("python_versions") {
    override val id: Column<EntityID<ULong>> = reference("id", Versions, onDelete = ReferenceOption.CASCADE)

    /**
     * Referenced by requirements, but never released.
     *
     * `Requires-Python` routinely bounds against versions that do not exist - `>=3.8,<4.0` is the
     * common idiom, and Python 4 has never shipped. Because `package_python_requirements` reaches
     * `python_version_specifiers` through a compound foreign key, an unreleased bound has nowhere
     * to point and the whole clause set was being discarded: 12% of packages declare one.
     *
     * Marking them rather than omitting them keeps "versions that exist" answerable - filter on
     * this - while letting a specifier reference a bound that does not.
     */
    val theoretical = bool("theoretical").default(false)

    /**
     * Whether we intend to *run* this interpreter, as opposed to merely resolve against it.
     *
     * A separate question from [theoretical], and 4.0.0 is why: it is the fifth most referenced
     * bound in the corpus - `<4` is near-universal - while being a release that has never existed.
     * Every row is a valid resolution target by construction, so nothing needs a flag for that;
     * this marks the far smaller set we will execute in and introspect the stdlib of.
     *
     * False below 3.4, where the stdlib is old enough that introspecting it is a different job.
     */
    val supportTarget = bool("support_target").default(false)

    /**
     * Which phase this interpreter gets *introspected* in: 1 now, 2 soon, 3 eventually.
     *
     * Set only on the terminal micro of each series, because introspection is per running
     * interpreter - `type_introspector` imports from whichever Python it is executed under, so a
     * pass costs a whole built environment. Two micros of one series would cost two environments
     * to produce near-identical type surfaces, so only the terminal is ever introspected and the
     * rest of its series inherits it.
     *
     * Null therefore means two different things depending on [supportTarget]: with it false, the
     * version is not supported at all; with it true, the version is perfectly runnable and simply
     * has no introspection pass of its own.
     *
     * Development bookkeeping rather than domain truth, and expected to be dropped once the passes
     * are done.
     */
    val supportPriority = ubyte("support_priority").nullable()

    override val primaryKey = PrimaryKey(id, name = "PK_python_versions_id")
}


object PythonVersionSpecifiers : OwleCompositeIdTable("python_version_specifiers") {
    val pythonVersion = keyReference("version_id", PythonVersions)
    val operator = dbEnum<VersionOperator>("operator", 16).entityId()
    val wildcard = dbEnum<WildcardSegment>("wildcard", 8).entityId()
    override val primaryKey = PrimaryKey(pythonVersion, operator, wildcard)
}


object Versions : ULongIdTable("versions") {
    val epoch = ulong("epoch").default(0uL)
    val major = ulong("major")
    val minor = ulong("minor").default(0uL)
    val micro = ulong("micro").default(0uL)
    val phase = dbEnum<PrereleasePhase>("phase", 16).default(PrereleasePhase.FINAL)
    val phaseNumber = ulong("phase_number").nullable()
    val postNumber = ulong("post_number").nullable()
    val devNumber = ulong("dev_number").nullable()
    val local = varchar("local", 255).nullable()
    val hasOverflow = bool("has_overflow").default(false)

    init {
        uniqueIndex(
            "uniq_normal_version",
            epoch, major, minor, micro,
            phase, phaseNumber,
            postNumber, devNumber, local
        )

        index("idx_version_components", false, major, minor, micro)

        check("versions_chk_phase") {
            ((phase eq PrereleasePhase.FINAL) and phaseNumber.isNull()) or
                ((phase neq PrereleasePhase.FINAL) and phaseNumber.isNotNull())
        }

        check("versions_chk_post") {
            (postNumber.isNotNull() and (phase eq PrereleasePhase.FINAL)) or postNumber.isNull()
        }

        check("versions_chk_dev") {
            (devNumber.isNotNull() and (phase eq PrereleasePhase.FINAL) and postNumber.isNull()) or devNumber.isNull()
        }
    }
}


object VersionOverflows : ULongIdTable("version_overflows") {
    val version = reference("version_id", Versions, onDelete = ReferenceOption.CASCADE)
    val violation = dbEnum<OverflowViolation>("violation", 32)
    val stringValue = varchar("string_value", 255)

    init {
        uniqueIndex("unique_component", version, violation)
    }
}


object EnvMarkerExpressions : ULongIdTable("env_marker_expressions") {
    val requirement = reference("requirement_id", PackageDistRequirements)
    val parent = reference("parent_id", EnvMarkerExpressions).nullable()
    val mode = dbEnum<EnvMarkerExpressionMode>("mode", 16)
}


object EnvMarkerLogicals : ULongIdTable("env_marker_logicals") {
    val expression = reference("expression_id", EnvMarkerExpressions, onDelete = ReferenceOption.CASCADE)
    val operator = dbEnum<EnvMarkerLogicOp>("operator", 4)
    val leftChild = reference("left_child_id", EnvMarkerExpressions)
    val rightChild = reference("right_child_id", EnvMarkerExpressions)
}


object EnvMarkerComparisons : ULongIdTable("env_marker_comparisons") {
    val expression = reference("expression_id", EnvMarkerExpressions, onDelete = ReferenceOption.CASCADE)
    val variable = dbEnum<EnvMarkerVariable>("variable", 32).nullable()
    val operator = dbEnum<EnvMarkerOperator>("operator", 18)
    val value = text("value")
}
