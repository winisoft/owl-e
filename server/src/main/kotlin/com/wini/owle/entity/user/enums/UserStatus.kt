package com.wini.owle.entity.user.enums

import kotlinx.serialization.Serializable


@Serializable
enum class UserStatus {

    REGISTERED,

    ACTIVE,

    CANCELLED,

    ON_HOLD,

    DELETED

}