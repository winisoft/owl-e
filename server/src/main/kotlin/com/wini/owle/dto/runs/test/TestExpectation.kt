package com.wini.owle.dto.runs.test

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ComparisonOperator
import kotlinx.serialization.Serializable

@Serializable
data class TestExpectation(
    override val id: Long,
    val testCase: TestCase,
    val dataPointName: String,
    val expectedValue: String?,
    val comparisonOperator: ComparisonOperator
) : StandardDto