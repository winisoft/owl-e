package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken

enum class WildcardSegment(override val dbValue: String) : DbToken {

    NONE("NONE"),

    MAJOR("MAJOR"),

    MINOR("MINOR"),

    MICRO("MICRO")

}