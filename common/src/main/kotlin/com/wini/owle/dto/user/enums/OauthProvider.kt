package com.wini.owle.dto.user.enums

import com.wini.owle.dto.DbToken

enum class OauthProvider(override val dbValue: String) : DbToken {

    GOOGLE("google"),

    GITHUB("github")

}