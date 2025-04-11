package com.wini.owle.models.metadata

import kotlinx.serialization.Serializable


@Serializable
data class StepInclusions(
    val includeAllSteps: Boolean = true,
    val excludedSteps: List<Int> = emptyList()
)