package com.wini.owle.dto.runs

import kotlinx.serialization.Serializable

@Serializable
sealed class ComparisonOperatorDto(val id: String) {

    @Serializable
    data object Equals : ComparisonOperatorDto("EQUALS")

    @Serializable
    data object NotEquals : ComparisonOperatorDto("NOT_EQUALS")

    @Serializable
    data object Contains : ComparisonOperatorDto("CONTAINS")

    @Serializable
    data object GreaterThan : ComparisonOperatorDto("GREATER_THAN")

    @Serializable
    data object LessThan : ComparisonOperatorDto("LESS_THAN")
}