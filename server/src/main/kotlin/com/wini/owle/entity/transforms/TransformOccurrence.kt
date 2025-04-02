package com.wini.owle.entity.transforms

import com.wini.owle.dto.transforms.TransformOccurrenceDto

enum class TransformOccurrence {
    PROVISION, REVISION, INQUISITION, PREREQINJECTION;

    companion object {
        fun fromDto(dto: TransformOccurrenceDto): TransformOccurrence {
            return when (dto) {
                TransformOccurrenceDto.Provision -> PROVISION
                TransformOccurrenceDto.Revision -> REVISION
                TransformOccurrenceDto.Inquisition -> INQUISITION
                TransformOccurrenceDto.PreReqInjection -> PREREQINJECTION
            }
        }
         fun TransformOccurrence.toDto(): TransformOccurrenceDto {
            return when (this) {
                PROVISION -> TransformOccurrenceDto.Provision
                REVISION -> TransformOccurrenceDto.Revision
                INQUISITION -> TransformOccurrenceDto.Inquisition
                PREREQINJECTION -> TransformOccurrenceDto.PreReqInjection
            }
        }
    }
}