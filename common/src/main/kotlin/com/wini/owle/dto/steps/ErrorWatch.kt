package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class ErrorWatch(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    val step: Step,

    val errorType: String,

    val triggerStep: Step

) : ULongDto