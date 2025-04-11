package com.wini.owle.dto.user

import kotlinx.serialization.Serializable

@Serializable
sealed class SubscriptionStatusDto(val id: String) {

    @Serializable
    data object Registered : SubscriptionStatusDto("REGISTERED")

    @Serializable
    data object Active : SubscriptionStatusDto("ACTIVE")

    @Serializable
    data object Cancelled : SubscriptionStatusDto("CANCELLED")

    @Serializable
    data object OnHold : SubscriptionStatusDto("ON_HOLD")
}