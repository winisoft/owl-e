package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.GenerationMethodDto

enum class GenerationMethod {
    MANUAL, AI_GENERATED;

    companion object {
        fun fromDto(dto: GenerationMethodDto): GenerationMethod {
            return when (dto) {
                GenerationMethodDto.Manual -> MANUAL
                GenerationMethodDto.AiGenerated -> AI_GENERATED
            }
        }
        fun GenerationMethod.toDto(): GenerationMethodDto {
            return when (this) {
                MANUAL -> GenerationMethodDto.Manual
                AI_GENERATED -> GenerationMethodDto.AiGenerated
            }
        }
    }
}