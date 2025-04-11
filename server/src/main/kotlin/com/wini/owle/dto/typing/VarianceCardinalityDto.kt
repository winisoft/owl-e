package com.wini.owle.dto.typing

import kotlinx.serialization.Serializable

@Serializable
sealed class VarianceCardinalityDto(val id: String) {

    @Serializable
    data object Covariant : VarianceCardinalityDto("COVARIANT")

    @Serializable
    data object Contravariant : VarianceCardinalityDto("CONTRAVARIANT")

    @Serializable
    data object Invariant : VarianceCardinalityDto("INVARIANT")
}