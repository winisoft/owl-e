package com.wini.owle.models.user.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUsage {
    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
}