package com.wini.owle.environs

import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.dto.environs.version.enums.VersionOperator
import com.wini.owle.dto.environs.version.enums.WildcardSegment
import com.wini.owle.environs.VersionSpec.Clause
import com.wini.owle.environs.VersionSpec.VersionComponents
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


/**
 * PEP 440 ordering and specifier evaluation.
 *
 * Worth testing directly rather than through a query, because every one of these rules is wrong in
 * a way nothing notices: a version that sorts wrongly produces a plausible answer in the wrong
 * order, and a specifier that admits too much produces a package list that looks fine until
 * something fails to install.
 */
@DisplayName("Version specifiers")
abstract class VersionSpecTest {

    private fun v(
        major: ULong,
        minor: ULong = 0uL,
        micro: ULong = 0uL,
        phase: PrereleasePhase = PrereleasePhase.FINAL,
        phaseNumber: ULong? = null,
        post: ULong? = null,
        dev: ULong? = null,
        epoch: ULong = 0uL,
        local: String? = null
    ) = VersionComponents(epoch, major, minor, micro, phase, phaseNumber, post, dev, local)

    @Nested
    @DisplayName("Ordering")
    inner class Ordering {

        /**
         * GIVEN two versions differing only in a release component
         * WHEN they are compared
         * THEN the comparison is numeric, not lexicographic
         */
        @Test
        fun `1_10 is newer than 1_9`() {
            assertTrue(VersionSpec.compare(v(1u, 10u), v(1u, 9u)) > 0)
            // the string comparison this exists to avoid
            assertTrue("1.10.0" < "1.9.0")
        }

        /**
         * GIVEN the full PEP 440 progression for one release
         * WHEN they are sorted
         * THEN dev precedes pre-releases, which precede final, which precedes post
         */
        @Test
        fun `orders dev before pre-release before final before post`() {
            val ordered = listOf(
                v(1u, dev = 1uL),
                v(1u, phase = PrereleasePhase.ALPHA, phaseNumber = 1uL),
                v(1u, phase = PrereleasePhase.BETA, phaseNumber = 1uL),
                v(1u, phase = PrereleasePhase.CANDIDATE, phaseNumber = 1uL),
                v(1u),
                v(1u, post = 1uL)
            )
            val shuffled = ordered.shuffled().sortedWith(VersionSpec.ORDERING)
            assertEquals(ordered, shuffled)
        }

        /**
         * GIVEN two versions differing only in epoch
         * WHEN they are compared
         * THEN epoch dominates every release component
         *
         * Epoch exists so a project can restart its numbering, which only works if it outranks
         * everything below it.
         */
        @Test
        fun `epoch outranks the release`() {
            assertTrue(VersionSpec.compare(v(1u, epoch = 1uL), v(999u)) > 0)
        }

        /**
         * GIVEN two pre-releases of the same phase
         * WHEN they are compared
         * THEN the phase number decides
         */
        @Test
        fun `pre-release numbers order within a phase`() {
            val a = v(1u, phase = PrereleasePhase.ALPHA, phaseNumber = 2uL)
            val b = v(1u, phase = PrereleasePhase.ALPHA, phaseNumber = 10uL)
            assertTrue(VersionSpec.compare(a, b) < 0)
        }
    }

    @Nested
    @DisplayName("Comparison operators")
    inner class Operators {

        /**
         * GIVEN a candidate and a bound
         * WHEN each ordering operator is applied
         * THEN it agrees with the ordering
         */
        @Test
        fun `ordering operators follow the comparison`() {
            val c = v(3u, 12u, 4u)
            val target = v(3u, 11u)
            assertTrue(VersionSpec.satisfies(c, VersionOperator.GREATER_THAN, target))
            assertTrue(VersionSpec.satisfies(c, VersionOperator.GREATER_THAN_OR_EQ, target))
            assertFalse(VersionSpec.satisfies(c, VersionOperator.LESS_THAN, target))
            assertTrue(VersionSpec.satisfies(c, VersionOperator.NOT_EQ, target))
        }

        /**
         * GIVEN `==1.4.*`
         * WHEN candidates in and out of that series are tested
         * THEN only those sharing major and minor match
         */
        @Test
        fun `a wildcard equality matches on the components left of the star`() {
            val target = v(1u, 4u)
            fun matches(c: VersionComponents) =
                VersionSpec.satisfies(c, VersionOperator.EQUALS, target, WildcardSegment.MINOR)

            assertTrue(matches(v(1u, 4u, 0u)))
            assertTrue(matches(v(1u, 4u, 17u)))
            assertFalse(matches(v(1u, 5u, 0u)))
            assertFalse(matches(v(2u, 4u, 0u)))
        }

        /**
         * GIVEN `~=2.2`
         * WHEN candidates are tested
         * THEN it admits 2.x at or above 2.2 and rejects 3.x
         *
         * `~=2.2` has two written segments, so PEP 440 pins the first and frees the second - the
         * wildcard is [WildcardSegment.MAJOR], meaning "compare major only", not the number of
         * segments that were written.
         */
        @Test
        fun `compatible release pins the component left of the last written`() {
            val target = v(2u, 2u)
            fun ok(c: VersionComponents) =
                VersionSpec.satisfies(c, VersionOperator.COMPATIBLE, target, WildcardSegment.MAJOR)

            assertTrue(ok(v(2u, 2u)))
            assertTrue(ok(v(2u, 9u, 3u)), "minor is free once major is pinned")
            assertFalse(ok(v(2u, 1u)), "below the lower bound")
            assertFalse(ok(v(3u, 0u)), "the implied upper bound")
        }

        /**
         * GIVEN `~=1.4.5`, whose three written segments pin major and minor
         * WHEN a candidate sharing major.minor but a newer micro is tested
         * THEN it is admitted
         *
         * The case [WildcardSegment.MAJOR] alone does not cover: a three-segment `~=` pins one
         * component more than a two-segment one does.
         */
        @Test
        fun `a three-segment compatible release pins major and minor`() {
            val target = v(1u, 4u, 5u)
            fun ok(c: VersionComponents) =
                VersionSpec.satisfies(c, VersionOperator.COMPATIBLE, target, WildcardSegment.MINOR)

            assertTrue(ok(v(1u, 4u, 9u)), "newer micro, same major.minor")
            assertFalse(ok(v(1u, 5u, 0u)), "minor is pinned here, unlike the two-segment case")
        }

        /**
         * GIVEN two versions equal in every ordered component but differing in local identifier
         * WHEN `===` is applied
         * THEN they are not equal, though every other operator sees them as identical
         */
        @Test
        fun `exact equality is the only operator that sees the local identifier`() {
            val a = v(1u, local = "ubuntu1")
            val b = v(1u, local = null)
            assertEquals(0, VersionSpec.compare(a, b))
            assertTrue(VersionSpec.satisfies(a, VersionOperator.EQUALS, b))
            assertFalse(VersionSpec.satisfies(a, VersionOperator.EXACT, b))
        }
    }

    @Nested
    @DisplayName("Clause sets")
    inner class ClauseSets {

        /**
         * GIVEN `>=3.9,<3.13` as two clauses
         * WHEN interpreters across the range are tested
         * THEN only those inside both bounds satisfy the set
         *
         * This is the shape a real Requires-Python takes, and the conjunction is what makes the
         * environment view's viable window an intersection.
         */
        @Test
        fun `a specifier set is a conjunction`() {
            val clauses = listOf(
                Clause(VersionOperator.GREATER_THAN_OR_EQ, v(3u, 9u)),
                Clause(VersionOperator.LESS_THAN, v(3u, 13u))
            )
            assertFalse(VersionSpec.satisfiesAll(v(3u, 8u), clauses))
            assertTrue(VersionSpec.satisfiesAll(v(3u, 9u), clauses))
            assertTrue(VersionSpec.satisfiesAll(v(3u, 12u, 4u), clauses))
            assertFalse(VersionSpec.satisfiesAll(v(3u, 13u), clauses))
        }

        /**
         * GIVEN a package declaring no Requires-Python
         * WHEN any interpreter is tested
         * THEN it is admitted
         *
         * The absence of the field on an index means "no constraint", not "no versions".
         */
        @Test
        fun `an empty clause set admits everything`() {
            assertTrue(VersionSpec.satisfiesAll(v(2u, 7u), emptyList()))
        }
    }

    @Nested
    @DisplayName("Rendering")
    inner class Rendering {

        /**
         * GIVEN clauses of each shape
         * WHEN rendered
         * THEN they read as they would be written in a requirement
         */
        @Test
        fun `renders clauses as they are written`() {
            assertEquals(">=3.9.0", VersionSpec.render(Clause(VersionOperator.GREATER_THAN_OR_EQ, v(3u, 9u))))
            assertEquals(
                "==1.4.*",
                VersionSpec.render(Clause(VersionOperator.EQUALS, v(1u, 4u), WildcardSegment.MINOR))
            )
            assertEquals(
                "~=2.2",
                VersionSpec.render(Clause(VersionOperator.COMPATIBLE, v(2u, 2u), WildcardSegment.MAJOR))
            )
        }
    }
}
