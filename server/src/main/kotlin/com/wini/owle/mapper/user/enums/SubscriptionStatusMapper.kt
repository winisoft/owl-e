package com.wini.owle.mapper.user.enums

import com.wini.owle.dto.user.enums.SubscriptionStatus
import com.wini.owle.entity.user.enums.SubscriptionStatusEntity

class SubscriptionStatusMapper {

    fun toEntity(dto: SubscriptionStatus): SubscriptionStatusEntity {
        return when (dto) {
            SubscriptionStatus.REGISTERED -> SubscriptionStatusEntity.REGISTERED
            SubscriptionStatus.ACTIVE -> SubscriptionStatusEntity.ACTIVE
            SubscriptionStatus.CANCELLED -> SubscriptionStatusEntity.CANCELLED
            SubscriptionStatus.ON_HOLD -> SubscriptionStatusEntity.ON_HOLD
        }
    }

    fun toDto(entity: SubscriptionStatusEntity): SubscriptionStatus {
        return when(entity){
            SubscriptionStatusEntity.REGISTERED -> SubscriptionStatus.REGISTERED
            SubscriptionStatusEntity.ACTIVE -> SubscriptionStatus.ACTIVE
            SubscriptionStatusEntity.CANCELLED -> SubscriptionStatus.CANCELLED
            SubscriptionStatusEntity.ON_HOLD -> SubscriptionStatus.ON_HOLD
        }
    }
}