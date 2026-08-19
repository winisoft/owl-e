package com.wini.owle.dto.environs.enums

import com.wini.owle.dto.DbToken


/**
 * The environment-marker variables, per PEP 508.
 *
 * A closed set, which is why the column is this enum rather than free text: a marker naming
 * something outside it - `sys.platform`, `python-version` - is not a condition that happens to be
 * false, it is a marker nobody can evaluate. Stored as text such a typo ingests cleanly and the
 * dependency silently never applies, with nothing to say why. As an enum it fails at load.
 *
 * The spelling is the one used in the marker itself, which is also what the column stores.
 */
enum class EnvMarkerVariable(override val dbValue: String) : DbToken {

    /** `major.minor` of the running interpreter - compares as a PEP 440 version, not as text. */
    PYTHON_VERSION("python_version"),

    /** The full `major.minor.micro`, likewise version-compared. */
    PYTHON_FULL_VERSION("python_full_version"),

    OS_NAME("os_name"),

    SYS_PLATFORM("sys_platform"),

    PLATFORM_MACHINE("platform_machine"),

    PLATFORM_PYTHON_IMPLEMENTATION("platform_python_implementation"),

    PLATFORM_RELEASE("platform_release"),

    PLATFORM_VERSION("platform_version"),

    IMPLEMENTATION_NAME("implementation_name"),

    IMPLEMENTATION_VERSION("implementation_version"),

    /**
     * Not a property of the machine but of the request: true when the caller asked for that
     * optional feature set. This is what makes `pytest ; extra == "dev"` work.
     */
    EXTRA("extra")
}
