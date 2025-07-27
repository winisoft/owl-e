package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ComparisonOperator
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class TestExpectation(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    val testCase: TestCase,

    val dataPointName: String,

    @ConstraintSimple([StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VALUE_LENGTH)
    val expectedValue: String?,

    val comparisonOperator: ComparisonOperator

) : StandardDto {

    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
        const val MAX_VALUE_LENGTH = 16_000
    }
}