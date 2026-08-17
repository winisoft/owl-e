package com.wini.owle.dto.workflows

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Membership of a workflow in a Multiflow, with its placement on that canvas.
 */
@ModelDefinition
@Serializable
data class MultiflowWorkflow(

    val multiflow: Multiflow,

    val workflow: Workflow,

    val posX: Double,

    val posY: Double,

    val color: String?,

    val isCollapsed: Boolean,

) : CompositeDto {

    override val owleId: String get() = "multiflow=${multiflow.owleId},workflow=${workflow.owleId}"
}
