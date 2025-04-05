package com.wini.owle.entity.workflows.enums

import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUse {
    PERSONAL_NON_PROFIT, PERSONAL_PROFIT, CORPORATION
}