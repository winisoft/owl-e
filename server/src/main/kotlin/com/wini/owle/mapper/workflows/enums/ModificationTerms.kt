package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.ModificationTermsDto
import com.wini.owle.entity.workflows.enums.ModificationTerms


class ModificationTermsMapper {

    fun toEntity(dto: ModificationTermsDto): ModificationTerms {
        return when(dto) {
            ModificationTermsDto.Prohibited -> ModificationTerms.PROHIBITED
            ModificationTermsDto.AllowModification -> ModificationTerms.ALLOW_MODIFICATION
            ModificationTermsDto.AllowModificationDistribution -> ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION
        }
    }

    fun toDto(entity: ModificationTerms): ModificationTermsDto {
        return when(entity) {
            ModificationTerms.ALLOW_MODIFICATION -> ModificationTermsDto.AllowModification
            ModificationTerms.ALLOW_MODIFICATION_REDISTRIBUTION -> ModificationTermsDto.AllowModificationDistribution
            ModificationTerms.PROHIBITED -> ModificationTermsDto.Prohibited
        }
    }
}