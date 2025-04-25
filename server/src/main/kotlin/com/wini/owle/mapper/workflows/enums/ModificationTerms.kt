package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.ModificationTermsDto
import com.wini.owle.entity.workflows.enums.ModificationTermsEntity


class ModificationTermsMapper {

    fun toEntity(dto: ModificationTermsDto): ModificationTermsEntity {
        return when(dto) {
            ModificationTermsDto.PROHIBITED -> ModificationTermsEntity.PROHIBITED
            ModificationTermsDto.ALLOW_MODIFICATION -> ModificationTermsEntity.ALLOW_MODIFICATION
            ModificationTermsDto.ALLOW_MODIFICATION_DISTRIBUTION -> ModificationTermsEntity.ALLOW_MODIFICATION_REDISTRIBUTION
        }
    }

    fun toDto(entity: ModificationTermsEntity): ModificationTermsDto {
        return when(entity) {
            ModificationTermsEntity.ALLOW_MODIFICATION -> ModificationTermsDto.ALLOW_MODIFICATION
            ModificationTermsEntity.ALLOW_MODIFICATION_REDISTRIBUTION -> ModificationTermsDto.ALLOW_MODIFICATION_DISTRIBUTION
            ModificationTermsEntity.PROHIBITED -> ModificationTermsDto.PROHIBITED
        }
    }
}