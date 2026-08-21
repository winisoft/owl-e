package com.wini.owle.environs

import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


/**
 * Evaluating a PEP 508 marker against a target environment.
 *
 * The decision this makes — does `pywin32` belong in this environment — is invisible when wrong:
 * a marker that never matches silently drops a dependency, and one that always matches silently
 * adds one. Neither raises anything.
 */
@DisplayName("Marker evaluation")
abstract class MarkerEvaluationTest {

    private fun compare(
        variable: EnvMarkerVariable,
        operator: EnvMarkerOperator,
        value: String
    ) = MarkerNode.Comparison(variable, operator, value)

    private val linux312 = MarkerEnvironment(
        pythonVersion = "3.12",
        pythonFullVersion = "3.12.4",
        sysPlatform = "linux",
        osName = "posix"
    )

    private val windows39 = MarkerEnvironment(
        pythonVersion = "3.9",
        pythonFullVersion = "3.9.22",
        sysPlatform = "win32",
        osName = "nt"
    )

    /**
     * GIVEN `python_version >= "3.10"`
     * WHEN evaluated against 3.9 and 3.12
     * THEN the comparison is by version, not by text
     *
     * The case that makes string comparison unusable: `"3.10" < "3.9"` lexicographically, so a
     * text comparison would exclude every 3.10-and-later interpreter from a marker meant to
     * include them.
     */
    @Test
    fun `python_version compares as a version rather than as text`() {
        val marker = compare(EnvMarkerVariable.PYTHON_VERSION, EnvMarkerOperator.GE, "3.10")
        assertTrue(marker.evaluate(linux312), "3.12 should satisfy >= 3.10")
        assertFalse(marker.evaluate(windows39), "3.9 should not satisfy >= 3.10")
        assertTrue("3.10" < "3.9", "the text comparison this avoids")
    }

    /**
     * GIVEN `sys_platform == "win32"`
     * WHEN evaluated on each platform
     * THEN it holds only on Windows
     */
    @Test
    fun `platform comparisons hold only on the matching platform`() {
        val marker = compare(EnvMarkerVariable.SYS_PLATFORM, EnvMarkerOperator.EQ, "win32")
        assertTrue(marker.evaluate(windows39))
        assertFalse(marker.evaluate(linux312))
    }

    /**
     * GIVEN `python_version >= "3.8" and (sys_platform == "win32" or extra == "dev")`
     * WHEN evaluated in environments differing in platform and requested extras
     * THEN the tree combines correctly
     *
     * The nesting case the schema shreds into five rows, reassembled and evaluated.
     */
    @Test
    fun `a nested tree evaluates as the expression reads`() {
        val marker = MarkerNode.Logical(
            EnvMarkerLogicOp.AND,
            compare(EnvMarkerVariable.PYTHON_VERSION, EnvMarkerOperator.GE, "3.8"),
            MarkerNode.Logical(
                EnvMarkerLogicOp.OR,
                compare(EnvMarkerVariable.SYS_PLATFORM, EnvMarkerOperator.EQ, "win32"),
                compare(EnvMarkerVariable.EXTRA, EnvMarkerOperator.EQ, "dev")
            )
        )

        assertTrue(marker.evaluate(windows39), "windows satisfies the right branch")
        assertFalse(marker.evaluate(linux312), "linux without the extra satisfies neither")
        assertTrue(
            marker.evaluate(linux312.copy(extras = setOf("dev"))),
            "linux with the dev extra satisfies the right branch"
        )
        assertFalse(
            marker.evaluate(windows39.copy(pythonVersion = "3.7")),
            "the left branch fails regardless of platform"
        )
    }

    /**
     * GIVEN `extra == "dev"`
     * WHEN the caller did and did not ask for that extra
     * THEN it follows the request rather than the machine
     *
     * `extra` is the one marker variable that is not a property of any environment — it says what
     * was asked for, which is what makes optional dependency sets work.
     */
    @Test
    fun `extra follows the request, not the machine`() {
        val marker = compare(EnvMarkerVariable.EXTRA, EnvMarkerOperator.EQ, "dev")
        assertFalse(marker.evaluate(linux312))
        assertTrue(marker.evaluate(linux312.copy(extras = setOf("dev"))))
        assertFalse(marker.evaluate(linux312.copy(extras = setOf("docs"))))
    }

    /**
     * GIVEN a comparison whose variable could not be resolved
     * WHEN evaluated
     * THEN it does not hold
     *
     * Now that the column is an enum an unknown variable cannot be stored, so this is the
     * belt-and-braces case rather than the expected one — but a null must not evaluate to true,
     * which would silently make a conditional dependency unconditional.
     */
    @Test
    fun `an unresolved variable does not hold`() {
        val marker = MarkerNode.Comparison(null, EnvMarkerOperator.EQ, "anything")
        assertFalse(marker.evaluate(linux312))
    }

    /**
     * GIVEN a marker tree
     * WHEN asked which variables it consults
     * THEN every leaf's variable is reported
     */
    @Test
    fun `reports the variables it consults`() {
        val marker = MarkerNode.Logical(
            EnvMarkerLogicOp.AND,
            compare(EnvMarkerVariable.PYTHON_VERSION, EnvMarkerOperator.GE, "3.8"),
            compare(EnvMarkerVariable.SYS_PLATFORM, EnvMarkerOperator.EQ, "win32")
        )
        assertEquals(
            setOf(EnvMarkerVariable.PYTHON_VERSION, EnvMarkerVariable.SYS_PLATFORM),
            marker.variables()
        )
    }

    /**
     * GIVEN a tree mixing `and` with `or`
     * WHEN rendered
     * THEN the differing operator is parenthesised so the reading is unambiguous
     */
    @Test
    fun `renders with parentheses only where the operator changes`() {
        val marker = MarkerNode.Logical(
            EnvMarkerLogicOp.AND,
            compare(EnvMarkerVariable.PYTHON_VERSION, EnvMarkerOperator.GE, "3.8"),
            MarkerNode.Logical(
                EnvMarkerLogicOp.OR,
                compare(EnvMarkerVariable.SYS_PLATFORM, EnvMarkerOperator.EQ, "win32"),
                compare(EnvMarkerVariable.EXTRA, EnvMarkerOperator.EQ, "dev")
            )
        )
        assertEquals(
            "python_version >= \"3.8\" and (sys_platform == \"win32\" or extra == \"dev\")",
            marker.render()
        )
    }
}
