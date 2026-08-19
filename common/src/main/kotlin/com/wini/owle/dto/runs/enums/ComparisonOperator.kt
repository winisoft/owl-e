package com.wini.owle.dto.runs.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class ComparisonOperator(override val dbValue: String) : DbToken {

    EQUALS("equals"),

    NOT_EQUALS("notEquals"),

    CONTAINS("contains"),

    GREATER_THAN("greaterThan"),

    LESS_THAN("lessThan")

}