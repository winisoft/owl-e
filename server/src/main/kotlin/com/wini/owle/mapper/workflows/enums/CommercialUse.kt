package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.CommercialUseDto
import com.wini.owle.entity.workflows.enums.CommercialUse


class CommercialUseMapper {

    fun toEntity(dto: CommercialUseDto): CommercialUse {
        return when(dto) {
            CommercialUseDto.Corporation -> CommercialUse.CORPORATION
            CommercialUseDto.PersonalProfit -> CommercialUse.PERSONAL_PROFIT
            CommercialUseDto.PersonalNonProfit -> CommercialUse.PERSONAL_NON_PROFIT
        }
    }

    fun toDto(entity: CommercialUse): CommercialUseDto {
        return when(entity) {
            CommercialUse.CORPORATION -> CommercialUseDto.Corporation
            CommercialUse.PERSONAL_PROFIT -> CommercialUseDto.PersonalProfit
            CommercialUse.PERSONAL_NON_PROFIT -> CommercialUseDto.PersonalNonProfit
        }
    }
}