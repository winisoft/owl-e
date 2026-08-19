package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken

enum class VersionOperator(override val dbValue: String) : DbToken {

    LESS_THAN("LT"),

    LESS_THAN_OR_EQ("LE"),

    NOT_EQ("NE"),

    EQUALS("EQ"),

    GREATER_THAN_OR_EQ("GE"),

    GREATER_THAN("GT"),

    COMPATIBLE("COMPATIBLE"),

    EXACT("EXACT");

}
