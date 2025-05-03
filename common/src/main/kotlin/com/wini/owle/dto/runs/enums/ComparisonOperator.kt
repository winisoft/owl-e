package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ComparisonOperator {

    EQUALS,

    NOT_EQUALS,

    CONTAINS,

    GREATER_THAN,

    LESS_THAN

}