package com.wini.owle.mapper.transforms.enums

import com.wini.owle.dto.transforms.TransformOccurrenceDto
import com.wini.owle.entity.transforms.enums.TransformOccurrence

class TransformOccurrenceMapper {

    fun toEntity(dto: TransformOccurrenceDto): TransformOccurrence {
        return when (dto) {
            TransformOccurrenceDto.Provision -> TransformOccurrence.PROVISION
            TransformOccurrenceDto.Revision -> TransformOccurrence.REVISION
            TransformOccurrenceDto.Inquisition -> TransformOccurrence.INQUISITION
            TransformOccurrenceDto.PreReqInjection -> TransformOccurrence.PREREQINJECTION
        }
    }

    fun toDto(entity: TransformOccurrence): TransformOccurrenceDto {
        return when (entity) {
            TransformOccurrence.PROVISION -> TransformOccurrenceDto.Provision
            TransformOccurrence.REVISION -> TransformOccurrenceDto.Revision
            TransformOccurrence.INQUISITION -> TransformOccurrenceDto.Inquisition
            TransformOccurrence.PREREQINJECTION -> TransformOccurrenceDto.PreReqInjection
        }
    }

}