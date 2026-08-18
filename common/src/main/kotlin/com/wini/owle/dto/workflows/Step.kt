package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a step within a workflow, defining its tool dependency, next and previous steps,
 * and whether it serves as the workflow's head step.
 */
@ModelDefinition
@ApiEndpointDefinition("api.workflows.step", ["workflows"], "/step")
@Serializable
data class Step(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Name of the step, used for identification within the workflow.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Step.NAME_MAX_LENGTH)
    val name: String,

    /**
     * Optional description of the step's purpose or action.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Step.DESC_MAX_LENGTH)
    val description: String?,

    /**
     * The phase within which this step is displayed. If null, it sits at the workflow level
     */
    val parentGrouper: Grouper?,

    /**
     * The step that follows when nothing has redirected. Null means this step is terminal.
     */
    val defaultNextStep: Step?,


    val posX: Double,

    val posY: Double,

    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Step.COLOR_LENGTH)
    val color: String?,

    val createdAt: String,

    val updatedAt: String,

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

    ) : ULongDto