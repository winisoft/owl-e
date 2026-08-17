package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.steps.errorWatch", ["step"], "/error-watch")
@Serializable
data class ErrorWatch(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    val step: Step,

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.ErrorWatch.ERROR_TYPE_MAX_LENGTH)
    val errorType: String,

    val triggerStep: Step

) : ULongDto