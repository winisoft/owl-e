package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.TestAssertion
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class TestOutcomeExpectation(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    //val testCase: TestCase,

    val dataPoint: DataPoint,

//    @ConstraintSimple([StringContainsRestrictedChars::class])
//    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16000)
//    val expectedValue: String?,

    val assertion: TestAssertion

) : ULongDto