package com.wini.owle.dto.workflows

import com.wini.owle.dto.ULongDto
import com.wini.owle.schema.ModelDefinition

@ModelDefinition
data class StepDefaultRoute(
    override val id: ULong,
    val nextStep: Step,
    val workflow: Workflow
): ULongDto