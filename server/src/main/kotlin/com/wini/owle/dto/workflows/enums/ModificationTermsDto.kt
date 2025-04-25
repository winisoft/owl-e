package com.wini.owle.dto.workflows.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ModificationTermsDto {
    PROHIBITED,
    ALLOW_MODIFICATION,
    ALLOW_MODIFICATION_DISTRIBUTION
}