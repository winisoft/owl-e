package com.wini.owle.models.user.metadata

import kotlinx.serialization.Serializable

@Serializable
data class SharingConfigDto(
    val includeAllSteps: Boolean = true,
    val excludedSteps: List<Int> = emptyList()
)