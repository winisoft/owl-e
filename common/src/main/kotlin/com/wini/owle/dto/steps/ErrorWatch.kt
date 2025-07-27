package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Step
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class ErrorWatch(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    val step: Step,

    val errorType: String,

    val triggerStep: Step

) : StandardDto