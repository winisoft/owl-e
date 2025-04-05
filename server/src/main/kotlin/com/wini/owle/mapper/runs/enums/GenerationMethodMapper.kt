package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.GenerationMethodDto
import com.wini.owle.entity.runs.enums.GenerationMethod

class GenerationMethodMapper {

    fun toEntity(dto: GenerationMethodDto): GenerationMethod {
        return when (dto) {
            GenerationMethodDto.Manual -> GenerationMethod.MANUAL
            GenerationMethodDto.AiGenerated -> GenerationMethod.AI_GENERATED
        }
    }

    fun toDto(entity: GenerationMethod): GenerationMethodDto {
        return when (entity) {
            GenerationMethod.MANUAL -> GenerationMethodDto.Manual
            GenerationMethod.AI_GENERATED -> GenerationMethodDto.AiGenerated
        }
    }

}