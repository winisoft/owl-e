package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken


enum class PrereleasePhase(override val dbValue: String) : DbToken {

    FINAL("final"),

    ALPHA("alpha"),

    BETA("beta"),

    CANDIDATE("candidate")

}