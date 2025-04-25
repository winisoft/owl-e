package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.CommercialUse
import com.wini.owle.entity.workflows.enums.CommercialUseEntity


class CommercialUseMapper {

    fun toEntity(dto: CommercialUse): CommercialUseEntity {
        return when(dto) {
            CommercialUse.CORPORATION -> CommercialUseEntity.CORPORATION
            CommercialUse.PERSONAL_PROFIT -> CommercialUseEntity.PERSONAL_PROFIT
            CommercialUse.PERSONAL_NONPROFIT -> CommercialUseEntity.PERSONAL_NON_PROFIT
        }
    }

    fun toDto(entity: CommercialUseEntity): CommercialUse {
        return when(entity) {
            CommercialUseEntity.CORPORATION -> CommercialUse.CORPORATION
            CommercialUseEntity.PERSONAL_PROFIT -> CommercialUse.PERSONAL_PROFIT
            CommercialUseEntity.PERSONAL_NON_PROFIT -> CommercialUse.PERSONAL_NONPROFIT
        }
    }
}