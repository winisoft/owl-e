package com.wini.owle.dto.workflows.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ModificationTerms {
    PROHIBITED,
    ALLOW_MODIFICATION,
    ALLOW_MODIFICATION_DISTRIBUTION
}