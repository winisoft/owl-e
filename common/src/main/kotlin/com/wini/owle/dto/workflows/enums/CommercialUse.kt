package com.wini.owle.dto.workflows.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUse(override val dbValue: String) : DbToken {
    PERSONAL_NON_PROFIT("PERSONAL_NON_PROFIT"),
    PERSONAL_PROFIT("PERSONAL_PROFIT"),
    CORPORATION("CORPORATION")
}