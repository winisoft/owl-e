package com.wini.owle.dto.environs.version.enums

import com.wini.owle.dto.DbToken

enum class OverflowViolation(override val dbValue: String) : DbToken {

    EPOCH("epoch"),

    MAJOR("major"),

    MINOR("minor"),

    MICRO("micro"),

    MICRO_EXTRA("micro_extra"),

    PHASE_NUMBER("phase_number"),

    POST_NUMBER("post_number"),

    DEV_NUMBER("dev_number");

}