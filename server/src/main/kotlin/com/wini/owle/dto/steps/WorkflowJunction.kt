package com.wini.owle.dto.steps

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import kotlinx.serialization.Serializable

/**
 * Represents a junction connecting one workflow to another, allowing a transition to a target workflow.
 * A junction may include conditions under which the transition is triggered,
 * and prerequisite injections required for the target workflow.
 */
@OwleModel
@Serializable
data class WorkflowJunction(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Step in the source workflow from which this transition originates.
     */
    val sourceStep: Step,

    /**
     * Workflow that this junction transitions to.
     */
    val targetWorkflow: Workflow,

    /**
     * Inquisition condition required to activate this junction, if specified.
     */
    val triggerInquisition: Inquisition?

) : StandardDto