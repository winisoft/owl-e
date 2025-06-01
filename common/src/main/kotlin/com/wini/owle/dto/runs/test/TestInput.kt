package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.GenerationMethod
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class TestInput(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    val testCase: TestCase,

    val dataPoint: DataPoint,

    @ConstraintSimple([StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_64000)
    val value: String?,

    val generationMethod: GenerationMethod

) : ULongDto