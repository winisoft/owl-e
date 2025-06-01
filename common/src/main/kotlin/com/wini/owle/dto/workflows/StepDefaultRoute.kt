package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto

data class StepDefaultRoute(
    val antecedentStep: Step,
    val defaultNext: Step
): CompositeDto {

    override val owleId: String
        get() = ""

}