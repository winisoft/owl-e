package com.wini.owle.environs

import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment


/**
 * PEP 440 version ordering and specifier evaluation.
 *
 * Deliberately pure and free of Exposed: this is the rule that decides whether `pandas 2.2.3` may
 * run on Python 3.12, and it wants to be testable without a database in front of it. The queries in
 * `com.wini.owle.query` supply the rows; this decides what they mean.
 *
 * Everything here operates on [VersionComponents] rather than on entities or DTOs so that the same
 * comparison serves a `versions` row, a parsed specifier, and a value typed by a user.
 */
object VersionSpec {

    /**
     * A version, decomposed. Mirrors the `versions` columns that participate in ordering.
     *
     * `local` is carried but does not participate in [compare] — PEP 440 orders local identifiers
     * segment-wise and only against each other, and they are not permitted on a public index at
     * all, so treating them as an opaque tag is the honest reading. [EXACT] is the one operator
     * that looks at it.
     */
    data class VersionComponents(
        val epoch: ULong = 0uL,
        val major: ULong,
        val minor: ULong = 0uL,
        val micro: ULong = 0uL,
        val phase: PrereleasePhase = PrereleasePhase.FINAL,
        val phaseNumber: ULong? = null,
        val postNumber: ULong? = null,
        val devNumber: ULong? = null,
        val local: String? = null
    )

    // -----------------------------------------------------------------------------------------
    // ordering
    // -----------------------------------------------------------------------------------------

    /**
     * The sort position of the pre-release segment.
     *
     * Three cases, and the first is the one that surprises people: a `.dev` release with no
     * pre-release and no post-release sorts **before** every pre-release of the same version, so
     * `1.0.dev1 < 1.0a1`. A version with no pre-release at all sorts *after* all of them, so
     * `1.0rc1 < 1.0`. Only an actual pre-release ranks among the others.
     */
    private fun preKey(v: VersionComponents): Pair<Int, ULong> = when {
        v.phase == PrereleasePhase.FINAL && v.postNumber == null && v.devNumber != null ->
            Int.MIN_VALUE to 0uL
        v.phase == PrereleasePhase.FINAL ->
            Int.MAX_VALUE to 0uL
        else ->
            v.phase.ordinal to (v.phaseNumber ?: 0uL)
    }

    /** Absent post sorts before any post: `1.0 < 1.0.post1`. */
    private fun postKey(v: VersionComponents): Pair<Int, ULong> =
        if (v.postNumber == null) Int.MIN_VALUE to 0uL else 0 to v.postNumber

    /** Absent dev sorts *after* any dev: `1.0.dev1 < 1.0`. */
    private fun devKey(v: VersionComponents): Pair<Int, ULong> =
        if (v.devNumber == null) Int.MAX_VALUE to 0uL else 0 to v.devNumber

    private fun cmp(a: Pair<Int, ULong>, b: Pair<Int, ULong>): Int =
        if (a.first != b.first) a.first.compareTo(b.first) else a.second.compareTo(b.second)

    /**
     * Total order over versions, per PEP 440.
     *
     * `1.0.dev1 < 1.0a1 < 1.0b1 < 1.0rc1 < 1.0 < 1.0.post1`, and `1.10.0 > 1.9.0` — which is the
     * whole reason versions are stored decomposed rather than as strings.
     */
    fun compare(a: VersionComponents, b: VersionComponents): Int {
        (a.epoch.compareTo(b.epoch)).let { if (it != 0) return it }
        (a.major.compareTo(b.major)).let { if (it != 0) return it }
        (a.minor.compareTo(b.minor)).let { if (it != 0) return it }
        (a.micro.compareTo(b.micro)).let { if (it != 0) return it }
        cmp(preKey(a), preKey(b)).let { if (it != 0) return it }
        cmp(postKey(a), postKey(b)).let { if (it != 0) return it }
        return cmp(devKey(a), devKey(b))
    }

    val ORDERING: Comparator<VersionComponents> = Comparator(::compare)

    // -----------------------------------------------------------------------------------------
    // specifiers
    // -----------------------------------------------------------------------------------------

    /**
     * How many release components a wildcard leaves comparable.
     *
     * The segment names *how many components are pinned*, not where the star sits - confirmed
     * against `determine_wildcard_segment` in `pyside/utils/version.py`, the code that actually
     * produces these values: `"1.*"` (one segment before the star) is [WildcardSegment.MAJOR],
     * `"1.4.*"` (two segments) is [WildcardSegment.MINOR], `"1.4.5.*"` (three) is
     * [WildcardSegment.MICRO]. This was inverted here before being checked against that source.
     */
    private fun comparedComponents(wildcard: WildcardSegment): Int = when (wildcard) {
        WildcardSegment.NONE -> 3
        WildcardSegment.MAJOR -> 1
        WildcardSegment.MINOR -> 2
        WildcardSegment.MICRO -> 3
    }

    /** Whether the release segments left of the wildcard agree. */
    private fun releaseMatches(
        candidate: VersionComponents,
        target: VersionComponents,
        wildcard: WildcardSegment
    ): Boolean {
        if (candidate.epoch != target.epoch) return false
        val depth = comparedComponents(wildcard)
        if (depth >= 1 && candidate.major != target.major) return false
        if (depth >= 2 && candidate.minor != target.minor) return false
        if (depth >= 3 && candidate.micro != target.micro) return false
        return true
    }

    /**
     * Whether [candidate] satisfies the clause `operator target`.
     *
     * One clause. A Requires-Python such as `>=3.9,<3.13` is several of these ANDed together, which
     * is [satisfiesAll]'s job.
     */
    fun satisfies(
        candidate: VersionComponents,
        operator: VersionOperator,
        target: VersionComponents,
        wildcard: WildcardSegment = WildcardSegment.NONE
    ): Boolean {
        val order = compare(candidate, target)
        return when (operator) {
            VersionOperator.LESS_THAN -> order < 0
            VersionOperator.LESS_THAN_OR_EQ -> order <= 0
            VersionOperator.GREATER_THAN -> order > 0
            VersionOperator.GREATER_THAN_OR_EQ -> order >= 0

            VersionOperator.EQUALS ->
                if (wildcard == WildcardSegment.NONE) order == 0
                else releaseMatches(candidate, target, wildcard)

            VersionOperator.NOT_EQ ->
                if (wildcard == WildcardSegment.NONE) order != 0
                else !releaseMatches(candidate, target, wildcard)

            /**
             * `~=` — at least the target, and no change to the component left of the last one
             * written. `~=2.2` means `>=2.2, ==2.*`; `~=1.4.5` means `>=1.4.5, ==1.4.*`.
             *
             * PEP 440 requires at least two release segments for `~=` and always wildcards the
             * last one written, so [WildcardSegment.NONE] is not a legal value here - there is no
             * such thing as a compatible-release clause with nothing wildcarded. [wildcard] is
             * used directly rather than defaulted, on the assumption the caller supplied one of
             * the three legal values.
             */
            VersionOperator.COMPATIBLE -> order >= 0 && releaseMatches(candidate, target, wildcard)

            /** `===` — arbitrary equality, the escape hatch. Local identifier included. */
            VersionOperator.EXACT -> order == 0 && candidate.local == target.local
        }
    }

    /** A specifier set is a conjunction: every clause must hold. An empty set admits everything. */
    fun satisfiesAll(
        candidate: VersionComponents,
        clauses: List<Clause>
    ): Boolean = clauses.all { satisfies(candidate, it.operator, it.target, it.wildcard) }

    data class Clause(
        val operator: VersionOperator,
        val target: VersionComponents,
        val wildcard: WildcardSegment = WildcardSegment.NONE
    )

    /**
     * `>=3.9`, `==1.4.*`, `~=2.2` — the clause as it would be written.
     *
     * `~=` never renders a literal `.*`: PEP 440's compatible-release syntax is `~=2.2`, not
     * `~=2.*`, even though the two mean the same range. And its written text has **one more**
     * segment than its wildcard names: the wildcard is the pin depth used for *matching*
     * (`~=2.2`'s wildcard is [WildcardSegment.MAJOR], meaning "compare major only"), but the
     * segment that gets freed by the wildcard was still *written* - `~=2.2` has two segments on
     * the page even though only the first is compared. Rendering has to restore that segment or
     * it prints `~=2`, which is not legal PEP 440 syntax at all (`~=` requires at least two).
     */
    fun render(clause: Clause): String {
        val symbol = when (clause.operator) {
            VersionOperator.LESS_THAN -> "<"
            VersionOperator.LESS_THAN_OR_EQ -> "<="
            VersionOperator.GREATER_THAN -> ">"
            VersionOperator.GREATER_THAN_OR_EQ -> ">="
            VersionOperator.EQUALS -> "=="
            VersionOperator.NOT_EQ -> "!="
            VersionOperator.COMPATIBLE -> "~="
            VersionOperator.EXACT -> "==="
        }
        val t = clause.target
        val release = if (clause.operator == VersionOperator.COMPATIBLE) {
            // one segment past the pin depth, per the note above; MICRO's true fourth segment
            // has no column to render from and is approximated at three
            when (clause.wildcard) {
                WildcardSegment.MAJOR -> "${t.major}.${t.minor}"
                WildcardSegment.MINOR, WildcardSegment.MICRO, WildcardSegment.NONE ->
                    "${t.major}.${t.minor}.${t.micro}"
            }
        } else {
            when (clause.wildcard) {
                WildcardSegment.MAJOR -> "${t.major}.*"
                WildcardSegment.MINOR -> "${t.major}.${t.minor}.*"
                WildcardSegment.MICRO -> "${t.major}.${t.minor}.${t.micro}.*"
                WildcardSegment.NONE -> "${t.major}.${t.minor}.${t.micro}"
            }
        }
        val epoch = if (t.epoch > 0uL) "${t.epoch}!" else ""
        return "$symbol$epoch$release"
    }
}
