package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a junction connecting one workflow to another, allowing a transition to a target workflow.
 * A junction may include conditions under which the transition is triggered,
 * and prerequisite injections required for the target workflow.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.workflowJunction", ["step"], "/workflow-junction")
@Serializable
data class WorkflowJunction(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Step in the source workflow from which this transition originates.
     */
    val sourceStep: Step,

    /**
     * Workflow that this junction transitions to.
     */
    val targetWorkflow: Workflow,
) : ULongDto