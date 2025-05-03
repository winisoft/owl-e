package com.wini.owle.dto.runs.test

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ComparisonOperator
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class TestExpectation(

    @LongValueMin(0)
    override val id: Long,

    val testCase: TestCase,

    val dataPointName: String,

    @StringMustNotContainRestricted
    @StringLengthMax(MAX_VALUE_LENGTH)
    val expectedValue: String?,

    val comparisonOperator: ComparisonOperator

) : StandardDto {

    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
        const val MAX_VALUE_LENGTH = 16_000
    }
}