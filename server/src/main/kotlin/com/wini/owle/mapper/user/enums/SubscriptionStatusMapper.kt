package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.SubscriptionStatusDto
import com.wini.owle.entity.user.enums.SubscriptionStatus

class SubscriptionStatusMapper {

    fun toEntity(dto: SubscriptionStatusDto): SubscriptionStatus {
        return when (dto) {
            SubscriptionStatusDto.Registered -> SubscriptionStatus.REGISTERED
            SubscriptionStatusDto.Active -> SubscriptionStatus.ACTIVE
            SubscriptionStatusDto.Cancelled -> SubscriptionStatus.CANCELLED
            SubscriptionStatusDto.OnHold -> SubscriptionStatus.ON_HOLD
        }
    }

    fun toDto(entity: SubscriptionStatus): SubscriptionStatusDto {
        return when(entity){
            SubscriptionStatus.REGISTERED -> SubscriptionStatusDto.Registered
            SubscriptionStatus.ACTIVE -> SubscriptionStatusDto.Active
            SubscriptionStatus.CANCELLED -> SubscriptionStatusDto.Cancelled
            SubscriptionStatus.ON_HOLD -> SubscriptionStatusDto.OnHold
        }
    }
}