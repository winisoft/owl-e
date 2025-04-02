package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TestExpectedOutcomeDto(
    val id: Long,
    val testCase: TestCaseDto,
    val dataPointName: String,
    val expectedValue: String?,
    val comparisonOperator: ComparisonOperatorDto
) : BaseDto