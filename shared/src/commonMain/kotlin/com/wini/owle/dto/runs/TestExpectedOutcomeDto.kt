package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TestExpectedOutcomeDto(
    override val id: Long,
    val testCase: TestCaseDto,
    val dataPointName: String,
    val expectedValue: String?,
    val comparisonOperator: ComparisonOperatorDto
) : StandardDto