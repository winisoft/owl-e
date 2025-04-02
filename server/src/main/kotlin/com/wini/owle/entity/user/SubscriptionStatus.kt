package com.wini.owle.entity.user

import com.wini.owle.dto.user.SubscriptionStatusDto


enum class SubscriptionStatus {
    REGISTERED, ACTIVE, CANCELLED, ON_HOLD;

    companion object {
        fun fromDto(dto: SubscriptionStatusDto): SubscriptionStatus {
            return when (dto) {
                SubscriptionStatusDto.Registered -> REGISTERED
                SubscriptionStatusDto.Active -> ACTIVE
                SubscriptionStatusDto.Cancelled -> CANCELLED
                SubscriptionStatusDto.OnHold -> ON_HOLD
            }
        }
        fun SubscriptionStatus.toDto(): SubscriptionStatusDto {
            return when(this){
                REGISTERED -> SubscriptionStatusDto.Registered
                ACTIVE -> SubscriptionStatusDto.Active
                CANCELLED -> SubscriptionStatusDto.Cancelled
                ON_HOLD -> SubscriptionStatusDto.OnHold
            }
        }
    }
}
