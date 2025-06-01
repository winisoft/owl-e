package com.wini.owle.dto.typing.enums

import kotlinx.serialization.Serializable

@Serializable
enum class VarianceCardinality {

    COVARIANT,

    CONTRAVARIANT,

    INVARIANT

}