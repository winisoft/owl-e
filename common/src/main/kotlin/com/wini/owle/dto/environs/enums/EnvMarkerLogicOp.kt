package com.wini.owle.dto.environs.enums

import com.wini.owle.dto.DbToken


enum class EnvMarkerLogicOp(override val dbValue: String) : DbToken {
    AND("AND"), OR("OR")
}