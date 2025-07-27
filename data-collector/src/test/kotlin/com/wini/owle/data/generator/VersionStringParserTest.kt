package com.wini.owle.data.generator

import com.wini.owle.dto.environs.PythonVersion
import com.wini.owle.dto.environs.enums.OverflowComponentType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class VersionStringParserTest {

    /**
     * GIVEN a list of normal PEP 440 version strings
     * WHEN each is parsed by the parser
     * THEN a non-null PythonVersion is returned with the original version string
     */
    @Test
    fun `parses normal PEP 440 versions`() {
        val validVersions = listOf(
            "2025.4.26", "3.10", "3.4.2", "2.32.3", "2.9.0.post0", "1.17.0", "2.4.0", "1.3.1", "0.5", "2.8.0",
            "4.13.2", "25.0", "80.3.1", "6.0.2", "25.1.1", "1.17.1", "2.22", "1.0.1", "0.6.1", "44.0.3"
        )
        validVersions.forEach {
            val parsed = VersionStringParser.parsePythonVersionString(versionStringRaw = it)
            assertNotNull(parsed, "Should parse: $it")
            assertEquals(it, parsed!!.versionString)
        }
    }

    /**
     * GIVEN an epoch version format is being parsed
     * WHEN it's evaluated for PEP-440, Letter-First or 'other'
     * THEN as a PEP-440 value, it returns a non-null PythonVersion that has a true 'hasOverflow' property
     */
    @Test
    fun `parses epoch-based versions with EPOCH overflow`() {
        val epochVersions = listOf(
            "1!3.1.0", "1!3.0.0", "1!1.2.2", "1!13.17.0", "1!2.0.2", "1!10.11.0", "5!4.2.2", "1!2020.1.30",
            "5!4.2.1", "1!6.0.0", "1!1.2.1", "3!1.7", "1746458771!10.1.0", "1746458685!11.4.0"
        )
        epochVersions.forEach {
            val parsed = VersionStringParser.parsePythonVersionString(versionStringRaw = it)
            assertNotNull(parsed, "Should parse epoch: $it")
            assertTrue(parsed!!.hasOverflow, "Epoch version should have overflow: $it")
        }
    }

    /**
     * GIVEN a year+letter version string (e.g. 2012c)
     * WHEN parsed by the parser
     * THEN a non-null PythonVersion is returned with hasOverflow true and major set to the year
     */
    @Test
    fun `parses year+letter versions with PHASE_NUMBER overflow`() {
        val yearLetter = listOf("2012c", "2004a", "2013b", "2010h")
        yearLetter.forEach {
            val parsed = VersionStringParser.parsePythonVersionString(versionStringRaw = it)
            assertNotNull(parsed, "Should parse year+letter: $it")
            assertTrue(parsed!!.hasOverflow, "Year+letter should have overflow: $it")
        }
    }

    /**
     * GIVEN a list of known-invalid version strings
     * WHEN each is parsed by the parser
     * THEN null is returned, indicating parse failure
     */
    @Test
    fun `fails on known-invalid versions`() {
        val invalids = listOf("dev", "my-version-number", "-0.2.0-", "r105", "pre-alpha", "Milestone1", "v-0.4")
        invalids.forEach {
            val parsed = VersionStringParser.parsePythonVersionString(versionStringRaw = it)
            assertNull(parsed, "Should fail to parse: $it")
        }
    }

    @Test
    fun `extractEpoch splits epoch and version correctly when provided a valid epoch version`() {
        assertEquals(Pair("3.1.0", "1"), VersionStringParser.extractEpoch("1!3.1.0"))
    }

    @Test
    fun `extractEpoch splits a null epoch version when no epoch is present to be split off`() {
        assertEquals(Pair("3.1.0", null), VersionStringParser.extractEpoch("3.1.0"))
    }

    @Test
    fun `extractEpoch splits a null epoch version when an invalid epoch version is provided`() {
        assertEquals(Pair("foo!bar!baz", null), VersionStringParser.extractEpoch("foo!bar!baz"))
    }

    @Test
    fun `isYearLetterVersion detects year+letter only`() {
        assertTrue(VersionStringParser.isYearLetterVersion("2012c"))
        assertTrue(VersionStringParser.isYearLetterVersion("2004a"))
        assertFalse(VersionStringParser.isYearLetterVersion("3.1.0"))
        assertFalse(VersionStringParser.isYearLetterVersion("1!3.1.0"))
    }

    @Test
    fun `isPep440Version detects valid PEP 440 versions`() {
        assertTrue(VersionStringParser.isPep440Version("3.1.0"))
        assertTrue(VersionStringParser.isPep440Version("3.1.0a1"))
        assertTrue(VersionStringParser.isPep440Version("3.1.0.post1"))
        assertTrue(VersionStringParser.isPep440Version("3.1.0.dev1"))
        assertTrue(VersionStringParser.isPep440Version("3.1"))
        assertTrue(VersionStringParser.isPep440Version("3"))
        assertFalse(VersionStringParser.isPep440Version("2012c"))
        assertFalse(VersionStringParser.isPep440Version("foo"))
    }

    @Test
    fun `parsePep440Version parses valid PEP 440 versions`() {
        val v = VersionStringParser.parsePep440Version(0UL, "3.1.0", null)
        assertNotNull(v)
        assertEquals(3UL, v!!.major)
        assertNotNull(v.minor)
        assertEquals(1UL, v.minor!!)
        assertNotNull(v.micro)
        assertEquals(0UL, v.micro!!)
        assertEquals(false, v.hasOverflow)

        val v2 = VersionStringParser.parsePep440Version(0UL, "3.1.0", "1")
        assertNotNull(v2)
        assertEquals(true, v2!!.hasOverflow)
    }

    @Test
    fun `parseYearLetterVersion parses year+letter versions`() {
        val v = VersionStringParser.parseYearLetterVersion(0UL, "2012c")
        assertNotNull(v)
        assertEquals(2012UL, v!!.major)
        assertEquals(true, v.hasOverflow)
        assertNull(v.minor)
        assertNull(v.micro)
    }

    /**
     * GIVEN various version string formats
     * WHEN each helper is called
     * THEN extractEpoch, isYearLetterVersion, isPep440Version, parsePep440Version, parseYearLetterVersion, and parsePythonVersionString behave as expected
     */
    @Test
    fun `parsePythonVersionString orchestrates helpers correctly`() {
        // PEP 440
        assertNotNull(VersionStringParser.parsePythonVersionString(versionStringRaw = "3.1.0"))
        // Epoch
        assertNotNull(VersionStringParser.parsePythonVersionString(versionStringRaw = "1!3.1.0"))
        // Year+letter
        assertNotNull(VersionStringParser.parsePythonVersionString(versionStringRaw = "2012c"))
        // Invalid
        assertNull(VersionStringParser.parsePythonVersionString(versionStringRaw = "foo"))
    }
} 