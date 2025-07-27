package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.PreventsTimeParadox
import kotlinx.serialization.Serializable

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
@ModelDefinition
@PreventsTimeParadox(
    happensFirstField = "startedAt",
    happensSecondField = "completedAt")
@Serializable
data class StepRun(

    @ConstraintSimple([PositiveValueLongError::class])
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
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val startedAt: String,

    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val completedAt: String?,

    /**
     * Execution status of the step, indicating current state.
     */
    val status: ExecutionStatus

) : StandardDto