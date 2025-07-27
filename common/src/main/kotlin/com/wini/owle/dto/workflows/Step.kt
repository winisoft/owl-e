package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
@ModelDefinition
@Serializable
data class Step(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Name of the step, used for identification within the workflow.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Optional description of the step's purpose or action.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_DESC_LENGTH)
    val description: String?,

    /**
     * The phase within which this step is displayed. If null, it sits at the workflow level
     */
    val parentPhase: Phase?,

    /**
     * The order this step is presented among other steps and phases sitting at the same level of the hierarchy
     */
    @ConstraintSimple([PositiveValueIntError::class])
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