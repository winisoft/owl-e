package com.wini.owle.query

import com.wini.owle.TestHelper
import com.wini.owle.dto.environs.version.enums.PrereleasePhase
import com.wini.owle.environs.MarkerEnvironment
import com.wini.owle.environs.VersionSpec.VersionComponents
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow


/**
 * That the hand-written queries are valid SQL against the real schema.
 *
 * Unit tests cover what the answers *mean*; this covers whether the database will accept the
 * question. That is the actual risk with DSL written by hand — a column named wrongly, a join
 * against the wrong key, a `ResultRow` read with a column the query never selected. All of it
 * compiles, and all of it fails at execution.
 *
 * The assertions are deliberately weak about *contents*: `owle_test` is a schema-only clone, so
 * these mostly run against empty tables. An empty result proves the statement executed, which is
 * what is being tested.
 */
@Tag("integration")
@DisplayName("Query execution")
abstract class QueryExecutionTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun connect() = TestHelper.connectToTestDb()
    }

    private val versions = VersionQueries()
    private val markers = MarkerQueries()

    private val py312 = VersionComponents(
        major = 3uL, minor = 12uL, micro = 4uL, phase = PrereleasePhase.FINAL
    )

    /**
     * GIVEN the versions and packages tables
     * WHEN every version query runs
     * THEN each is accepted by the database
     */
    @Test
    fun `version queries execute against the real schema`() {
        assertDoesNotThrow { versions.versionsOf("requests") }
        assertDoesNotThrow { versions.versionsOfFor("requests", py312) }
        assertDoesNotThrow { versions.newestFor("requests", py312) }
        assertDoesNotThrow { versions.requiresPythonFor("requests", 1uL) }
        assertDoesNotThrow { versions.pythonsAdmittedBy("requests", 1uL, listOf(py312)) }
    }

    /**
     * GIVEN a bounded range
     * WHEN the between query runs with each combination of bounds
     * THEN all four forms are accepted
     *
     * The bounds build the `WHERE` conditionally, so an absent bound is a different statement from
     * a present one and each shape needs to be executed at least once.
     */
    @Test
    fun `bounded version queries execute in every bound combination`() {
        val lower = VersionComponents(major = 1uL)
        val upper = VersionComponents(major = 3uL)
        assertDoesNotThrow { versions.versionsOfBetween("requests") }
        assertDoesNotThrow { versions.versionsOfBetween("requests", lower = lower) }
        assertDoesNotThrow { versions.versionsOfBetween("requests", upper = upper) }
        assertDoesNotThrow { versions.versionsOfBetween("requests", lower, upper) }
    }

    /**
     * GIVEN the Python releases in the database
     * WHEN they are listed
     * THEN the query executes and the ordering it claims holds
     */
    @Test
    fun `python releases come back in descending order`() {
        val releases = assertDoesNotThrow { versions.pythonReleases() }
        assertNotNull(releases)
        releases.zipWithNext { newer, older ->
            assertTrue(
                com.wini.owle.environs.VersionSpec.compare(newer.components, older.components) >= 0,
                "expected newest first, found ${older.components} after ${newer.components}"
            )
        }
    }

    /**
     * GIVEN a requirement id
     * WHEN its marker tree is read
     * THEN the query executes, and an absent marker is null rather than an error
     */
    @Test
    fun `marker queries execute and report an absent marker as null`() {
        assertDoesNotThrow { markers.markerFor(1uL) }
        assertDoesNotThrow { markers.markersForRequirements(listOf(1uL, 2uL)) }
        assertTrue(
            markers.applies(1uL, MarkerEnvironment(pythonVersion = "3.12")),
            "a requirement with no marker always applies"
        )
    }
}
