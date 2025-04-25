package com.wini.owle.dto.user.enums

import kotlinx.serialization.Serializable

@Serializable
enum class SubscriptionStatus {

    REGISTERED,

    ACTIVE,

    CANCELLED,

    ON_HOLD

}