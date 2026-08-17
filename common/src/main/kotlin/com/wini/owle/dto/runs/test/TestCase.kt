package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.workflow.TestOutcomeActual
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.ListMustNotBeEmpty
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.runs.test.testCase", ["runs"], "/test-case")
@Serializable
data class TestCase(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    @ConstraintSimple([StringNotEmptyError::class, StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    @ConstraintSimple([StringNotEmptyError::class, StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16000)
    val description: String?,

    val workflow: Workflow?,

    val step: Step?,

    val inputs: List<TestInput>,

    @ConstraintSimple([ListMustNotBeEmpty::class])
    val expectedOutcomes: List<TestOutcomeExpectation>,

    val results: List<TestOutcomeActual>

) : ULongDto