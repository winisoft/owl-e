package com.wini.owle.models.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUse {
    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
}