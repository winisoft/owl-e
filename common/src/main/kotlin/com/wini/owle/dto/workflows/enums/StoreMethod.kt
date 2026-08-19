package com.wini.owle.dto.workflows.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class StoreMethod(override val dbValue: String) : DbToken {

    TEXT("text"),

    BLOB("blob"),

    FILE("file")

}