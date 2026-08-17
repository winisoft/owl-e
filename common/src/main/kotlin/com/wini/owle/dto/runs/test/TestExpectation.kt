package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintLongValueMin
import com.wini.owle.error.LongValueMustMeetMinimum
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.runs.test.testExpectation", ["runs"], "/test-expectation")
@Serializable
data class TestExpectation(

    @ConstraintLongValueMin(LongValueMustMeetMinimum::class, 1)
    override val id: Long,

    val testCase: TestCase,

    val dataPointName: String,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VALUE_LENGTH)
    val expectedValue: String?,

    val comparisonOperator: ComparisonOperator

) : StandardDto {

    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
        const val MAX_VALUE_LENGTH = 16_000
    }
}