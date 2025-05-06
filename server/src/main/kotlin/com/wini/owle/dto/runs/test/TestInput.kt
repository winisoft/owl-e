package com.wini.owle.dto.runs.test

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.GenerationMethod
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class TestInput(
    @LongValueMin(0)
    override val id: Long,

    val testCase: TestCase,

    val dataPointName: String,

    @StringMustNotContainRestricted
    @StringLengthMax(MAX_VALUE_LENGTH)
    val value: String?,

    val generationMethod: GenerationMethod

) : StandardDto {

    companion object {
        const val MAX_VALUE_LENGTH = 64_000
    }
}