package com.wini.owle.dto.user.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable


@Serializable
enum class UserStatus(override val dbValue: String) : DbToken {

    REGISTERED("registered"),

    ACTIVE("active"),

    CANCELLED("cancelled"),

    ON_HOLD("on hold"),

    DELETED("deleted")

}