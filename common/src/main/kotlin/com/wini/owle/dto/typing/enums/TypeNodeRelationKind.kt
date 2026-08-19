package com.wini.owle.dto.typing.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class TypeNodeRelationKind(override val dbValue: String) : DbToken {

    INHERITANCE("inheritance"),

    INSTANTIATION("instantiation")

}
