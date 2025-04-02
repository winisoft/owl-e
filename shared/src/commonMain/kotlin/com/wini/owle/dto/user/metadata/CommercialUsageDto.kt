package com.wini.owle.dto.user.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUsageDto {
    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
}