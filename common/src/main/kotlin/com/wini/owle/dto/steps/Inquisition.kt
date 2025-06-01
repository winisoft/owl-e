package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

/**
 * Represents a conditional check or "inquisition" within a workflow,
 * analyzing a specific data point value and determining if workflow redirection is required.
 */
@ModelDefinition
@Serializable
data class Inquisition(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * The workflow step that includes this inquisition as a conditional check.
     */
    val step: Step,

    /**
     * Specifies the class of inquisition (e.g., condition type or criteria).
     */
    val condition: TypeNode,

    /**
     * The data point whose value is analyzed by this inquisition to determine workflow direction.
     */
    val dataPoint: DataPoint,

    /**
     * Specifies the workflow step to execute if the condition is met in this inquisition.
     */
    val onMetWorkflowStep: Step,

    /**
     * Determines the order in which this inquisition is executed within its associated workflow step.
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val executionOrder: Int

) : ULongDto