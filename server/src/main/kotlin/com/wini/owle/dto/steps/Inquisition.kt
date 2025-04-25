package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.DataPoint
import kotlinx.serialization.Serializable

/**
 * Represents a conditional check or "inquisition" within a workflow,
 * analyzing a specific data point value and determining if workflow redirection is required.
 */
@Serializable
data class Inquisition(
    override val id: Long,
    /**
     * The workflow step that includes this inquisition as a conditional check.
     */
    val step: Step,
    /**
     * Specifies the class of inquisition (e.g., condition type or criteria).
     */
    val className: String,
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
    val executionOrder: Int
) : StandardDto