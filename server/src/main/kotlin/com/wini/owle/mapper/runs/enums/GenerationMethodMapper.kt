package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.entity.runs.enums.GenerationMethodEntity

class GenerationMethodMapper {

    fun toEntity(dto: GenerationMethod): GenerationMethodEntity {
        return when (dto) {
            GenerationMethod.MANUAL -> GenerationMethodEntity.MANUAL
            GenerationMethod.AI_GENERATED -> GenerationMethodEntity.AI_GENERATED
        }
    }

    fun toDto(entity: GenerationMethodEntity): GenerationMethod {
        return when (entity) {
            GenerationMethodEntity.MANUAL -> GenerationMethod.MANUAL
            GenerationMethodEntity.AI_GENERATED -> GenerationMethod.AI_GENERATED
        }
    }

}