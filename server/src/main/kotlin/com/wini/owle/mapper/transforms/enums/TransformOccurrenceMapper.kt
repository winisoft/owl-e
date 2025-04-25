package com.wini.owle.mapper.transforms.enums

import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.entity.transforms.enums.TransformOccurrenceEntity

class TransformOccurrenceMapper {

    fun toEntity(dto: TransformOccurrence): TransformOccurrenceEntity {
        return when (dto) {
            TransformOccurrence.PROVISION -> TransformOccurrenceEntity.PROVISION
            TransformOccurrence.REVISION -> TransformOccurrenceEntity.REVISION
            TransformOccurrence.INQUISITION -> TransformOccurrenceEntity.INQUISITION
            TransformOccurrence.PREREQ_INJECTION -> TransformOccurrenceEntity.PREREQINJECTION
        }
    }

    fun toDto(entity: TransformOccurrenceEntity): TransformOccurrence {
        return when (entity) {
            TransformOccurrenceEntity.PROVISION -> TransformOccurrence.PROVISION
            TransformOccurrenceEntity.REVISION -> TransformOccurrence.REVISION
            TransformOccurrenceEntity.INQUISITION -> TransformOccurrence.INQUISITION
            TransformOccurrenceEntity.PREREQINJECTION -> TransformOccurrence.PREREQ_INJECTION
        }
    }

}