package com.wini.owle.dto.runs

import kotlinx.serialization.Serializable

@Serializable
sealed class GenerationMethodDto(val id: String) {

    @Serializable
    data object Manual : GenerationMethodDto("MANUAL")

    @Serializable
    data object AiGenerated : GenerationMethodDto("AI_GENERATED")
}