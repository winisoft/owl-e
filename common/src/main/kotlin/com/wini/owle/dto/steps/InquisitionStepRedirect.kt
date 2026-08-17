package com.wini.owle.dto.steps

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Where an inquisition routes when true, within the same workflow. Keyed on the inquisition.
 */
@ModelDefinition
@Serializable
data class InquisitionStepRedirect(

    override val id: ULong,

    val step: Step,

    val workflow: Workflow,

) : ULongDto
