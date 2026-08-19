package com.wini.owle.dto.workflows.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class AuthorshipCredit(override val dbValue: String) : DbToken {
    REQUIRED("REQUIRED"),
    UNNECESSARY("UNNECESSARY")
}