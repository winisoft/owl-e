package com.wini.owle.dto.runs.test

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * An assertion a test case makes about a data point's final value.
 */
@ModelDefinition
@ApiEndpointDefinition("api.runs.test.testExpectedOutcome", ["runs"], "/test-expected-outcome")
@Serializable
data class TestExpectedOutcome(

    override val id: ULong,

    val testCase: TestCase,

    val dataPoint: DataPoint,

    val expectedValue: String?,

    val operator: ComparisonOperator,

) : ULongDto
