package com.wini.owle.dto.workflows

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.IntValueMin
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
@OwleModel
@Serializable
data class Step(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Name of the step, used for identification within the workflow.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Optional description of the step's purpose or action.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val description: String?,

    /**
     * The phase within which this step is displayed. If null, it sits at the workflow level
     */
    val parentPhase: Phase?,

    /**
     * The order this step is presented among other steps and phases sitting at the same level of the hierarchy
     */
    @IntValueMin(0)
    val displayOrder: Int,

    /**
     * Workflow that contains this step.
     */
    val workflow: Workflow,

    /**
     * Indicates if this step is the first step in the workflow.
     */
    val isWorkflowHead: Boolean,

    /**
     * Tool associated with this step for execution.
     */
    val tool: Tool,

    /**
     * Reference to the next step in the workflow sequence.
     */
    val nextStep: Step?

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 512
        const val MAX_DESC_LENGTH = 16_000
    }
}