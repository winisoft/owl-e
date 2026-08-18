package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.runs.test.testInput", ["runs"], "/test-input")
@Serializable
data class TestInput(

    override val id: ULong,

    val testCase: TestCase,

    val dataPoint: DataPoint,

    @ConstraintSimple([StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_64000)
    val value: String?,
) : ULongDto