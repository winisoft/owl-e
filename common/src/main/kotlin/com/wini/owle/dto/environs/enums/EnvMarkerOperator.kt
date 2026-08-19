package com.wini.owle.dto.environs.enums

import com.wini.owle.dto.DbToken


/**
 * The PEP 508 environment-marker operators.
 *
 * The spelling is the operator itself, which is both what the column stores and how a marker is
 * written, so [toString] is the same value rather than a second copy of the table.
 */
enum class EnvMarkerOperator(override val dbValue: String) : DbToken {

    EQ("=="),
    NE("!="),
    LT("<"),
    LE("<="),
    GT(">"),
    GE(">="),
    IN("in"),
    NOT_IN("not in"),

    /** PEP 440 compatible-release. The column has always accepted it; the enum had no constant. */
    COMPATIBLE("~=");

    override fun toString(): String = dbValue
}
