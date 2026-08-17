package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition

@ModelDefinition
data class StepDefaultRoute(
    val antecedentStep: Step,
    val defaultNext: Step
): CompositeDto {

    override val owleId: String
        get() = ""

}