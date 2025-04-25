package com.wini.owle.dto.runs.step

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Step
import kotlinx.serialization.Serializable

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
@Serializable
data class StepExecution(
    override val id: Long,
    /**
     * Relationship to the associated workflow execution.
     */
    val workflowExecution: WorkflowExecution,
    /**
     * Relationship to the step associated with this execution.
     */
    val step: Step,
    /**
     * Timestamp for when the step execution started.
     */
    val startedAt: String,
    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    val completedAt: String?,
    /**
     * Execution status of the step, indicating current state.
     */
    val status: ExecutionStatus
) : StandardDto