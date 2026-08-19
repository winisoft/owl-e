package com.wini.owle.dto.typing.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class VarianceCardinality(override val dbValue: String) : DbToken {

    COVARIANT("covariant"),

    CONTRAVARIANT("contravariant"),

    INVARIANT("invariant")

}