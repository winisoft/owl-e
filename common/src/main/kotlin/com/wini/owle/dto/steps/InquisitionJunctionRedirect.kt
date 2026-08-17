package com.wini.owle.dto.steps

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * The junction an inquisition triggers when true. Keyed on the inquisition.
 */
@ModelDefinition
@Serializable
data class InquisitionJunctionRedirect(

    override val id: ULong,

    val junction: WorkflowJunction,

    val step: Step,

) : ULongDto
