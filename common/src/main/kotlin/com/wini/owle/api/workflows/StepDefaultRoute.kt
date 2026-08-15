package com.wini.owle.api.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.workflows.Step

data class StepDefaultRoute(
    val antecedentStep: Step,
    val defaultNext: Step
): CompositeDto {

    override val owleId: String
        get() = ""

}