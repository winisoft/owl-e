package com.wini.owle.environs

import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.dto.environs.version.enums.PrereleasePhase


/**
 * A PEP 508 environment marker, assembled into a tree and evaluable.
 *
 * The database stores markers shredded into rows so they can be queried; this is what those rows
 * mean. Pure — no Exposed, no database — so the rule that decides whether `pywin32` is required
 * here can be tested directly.
 */
sealed interface MarkerNode {

    /** `and` / `or`. Binary; a chain such as `a and b and c` is a left-nested pair of these. */
    data class Logical(
        val operator: EnvMarkerLogicOp,
        val left: MarkerNode,
        val right: MarkerNode
    ) : MarkerNode

    /** A leaf — `sys_platform == "win32"`. */
    data class Comparison(
        val variable: EnvMarkerVariable?,
        val operator: EnvMarkerOperator,
        val value: String
    ) : MarkerNode

    /**
     * Whether this marker holds in [environment].
     *
     * `and` and `or` short-circuit, which matters less for correctness than for what it says: a
     * marker is a boolean expression and behaves like one.
     */
    fun evaluate(environment: MarkerEnvironment): Boolean = when (this) {
        is Logical -> when (operator) {
            EnvMarkerLogicOp.AND -> left.evaluate(environment) && right.evaluate(environment)
            EnvMarkerLogicOp.OR -> left.evaluate(environment) || right.evaluate(environment)
        }
        is Comparison -> environment.satisfies(variable, operator, value)
    }

    /** Every variable this marker consults — what an environment must supply to evaluate it. */
    fun variables(): Set<EnvMarkerVariable> = when (this) {
        is Logical -> left.variables() + right.variables()
        is Comparison -> variable?.let { setOf(it) } ?: emptySet()
    }

    /** `python_version >= "3.8" and (sys_platform == "win32" or extra == "dev")`. */
    fun render(): String = when (this) {
        is Logical -> {
            val word = if (operator == EnvMarkerLogicOp.AND) "and" else "or"
            val l = if (left is Logical && left.operator != operator) "(${left.render()})" else left.render()
            val r = if (right is Logical && right.operator != operator) "(${right.render()})" else right.render()
            "$l $word $r"
        }
        is Comparison -> "${variable?.dbValue ?: "?"} $operator \"$value\""
    }
}


/**
 * The environment a marker is evaluated against.
 *
 * For Owl-E this is not the machine the UI runs on — it is the **target** the workflow will execute
 * in, which is known because execution is contained. That is what makes markers evaluable at design
 * time rather than only at install time.
 *
 * [extra] is the odd one out: not a property of any machine but of the *request*, true when the
 * caller asked for that optional feature set. It is what makes `pytest ; extra == "dev"` work.
 */
data class MarkerEnvironment(
    val pythonVersion: String,
    val pythonFullVersion: String = pythonVersion,
    val osName: String = "posix",
    val sysPlatform: String = "linux",
    val platformMachine: String = "x86_64",
    val platformPythonImplementation: String = "CPython",
    val platformRelease: String = "",
    /**
     * `platform.system()` - the operating system by its proper name: `Linux`, `Windows`, `Darwin`.
     *
     * Distinct from [sysPlatform], which is `sys.platform` and spells the same three targets
     * `linux`, `win32`, `darwin`. Packages gate on both, so both are carried. The default names the
     * contained execution target rather than the host this runs on, which is the whole reason these
     * are evaluable at design time.
     */
    val platformSystem: String = "Linux",
    val platformVersion: String = "",
    val implementationName: String = "cpython",
    val implementationVersion: String = pythonFullVersion,
    val extras: Set<String> = emptySet()
) {

    private fun valueOf(variable: EnvMarkerVariable): String? = when (variable) {
        EnvMarkerVariable.PYTHON_VERSION -> pythonVersion
        EnvMarkerVariable.PYTHON_FULL_VERSION -> pythonFullVersion
        EnvMarkerVariable.OS_NAME -> osName
        EnvMarkerVariable.SYS_PLATFORM -> sysPlatform
        EnvMarkerVariable.PLATFORM_MACHINE -> platformMachine
        EnvMarkerVariable.PLATFORM_PYTHON_IMPLEMENTATION -> platformPythonImplementation
        EnvMarkerVariable.PLATFORM_RELEASE -> platformRelease
        EnvMarkerVariable.PLATFORM_SYSTEM -> platformSystem
        EnvMarkerVariable.PLATFORM_VERSION -> platformVersion
        EnvMarkerVariable.IMPLEMENTATION_NAME -> implementationName
        EnvMarkerVariable.IMPLEMENTATION_VERSION -> implementationVersion
        // handled separately: `extra` is a set membership test, not a string comparison
        EnvMarkerVariable.EXTRA -> null
    }

    /**
     * Whether one comparison holds.
     *
     * Two of these compare as **versions rather than as text**, and it matters: `"3.10" < "3.9"` is
     * true lexicographically and false in fact, so a marker gating on `python_version >= "3.10"`
     * would exclude 3.10 itself if compared as a string.
     */
    fun satisfies(
        variable: EnvMarkerVariable?,
        operator: EnvMarkerOperator,
        value: String
    ): Boolean {
        // an unknown variable is not a false condition, it is an unevaluable marker; treating it
        // as false would silently drop the dependency, so it is treated as not applying and the
        // caller is expected to have rejected it at load
        val v = variable ?: return false

        if (v == EnvMarkerVariable.EXTRA) {
            return when (operator) {
                EnvMarkerOperator.EQ -> value in extras
                EnvMarkerOperator.NE -> value !in extras
                EnvMarkerOperator.IN -> value in extras
                EnvMarkerOperator.NOT_IN -> value !in extras
                else -> false
            }
        }

        val actual = valueOf(v) ?: return false

        if (v == EnvMarkerVariable.PYTHON_VERSION || v == EnvMarkerVariable.PYTHON_FULL_VERSION) {
            versionCompare(actual, value)?.let { order ->
                return when (operator) {
                    EnvMarkerOperator.EQ -> order == 0
                    EnvMarkerOperator.NE -> order != 0
                    EnvMarkerOperator.LT -> order < 0
                    EnvMarkerOperator.LE -> order <= 0
                    EnvMarkerOperator.GT -> order > 0
                    EnvMarkerOperator.GE -> order >= 0
                    EnvMarkerOperator.COMPATIBLE -> order >= 0
                    EnvMarkerOperator.IN -> actual in value
                    EnvMarkerOperator.NOT_IN -> actual !in value
                }
            }
        }

        return when (operator) {
            EnvMarkerOperator.EQ -> actual == value
            EnvMarkerOperator.NE -> actual != value
            EnvMarkerOperator.LT -> actual < value
            EnvMarkerOperator.LE -> actual <= value
            EnvMarkerOperator.GT -> actual > value
            EnvMarkerOperator.GE -> actual >= value
            EnvMarkerOperator.IN -> actual in value
            EnvMarkerOperator.NOT_IN -> actual !in value
            EnvMarkerOperator.COMPATIBLE -> actual == value
        }
    }

    /** Compares two dotted version strings numerically, or null when either is not one. */
    private fun versionCompare(a: String, b: String): Int? {
        val left = parse(a) ?: return null
        val right = parse(b) ?: return null
        return VersionSpec.compare(left, right)
    }

    private fun parse(text: String): VersionSpec.VersionComponents? {
        val parts = text.trim().split('.')
        if (parts.isEmpty() || parts.any { it.toULongOrNull() == null }) return null
        return VersionSpec.VersionComponents(
            major = parts.getOrNull(0)?.toULongOrNull() ?: return null,
            minor = parts.getOrNull(1)?.toULongOrNull() ?: 0uL,
            micro = parts.getOrNull(2)?.toULongOrNull() ?: 0uL,
            phase = PrereleasePhase.FINAL
        )
    }
}
