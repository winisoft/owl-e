package com.wini.owle.dto.environs.enums

import com.wini.owle.dto.DbToken


enum class EnvMarkerExpressionMode(override val dbValue: String) : DbToken {
      COMPARATIVE("COMPARATIVE"),

      LOGICAL("LOGICAL")

}