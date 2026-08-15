package com.wini.owle.entity.workflows.enums

import kotlinx.serialization.Serializable

@Serializable
enum class CommercialUse {
    PERSONAL_NONPROFIT,
    PERSONAL_PROFIT,
    CORPORATION
}