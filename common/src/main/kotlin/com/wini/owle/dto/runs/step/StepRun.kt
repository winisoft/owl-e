package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowExecution
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.PreventsTimeParadoxes
import kotlinx.serialization.Serializable

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
@OwleModel
@PreventsTimeParadoxes(
    happensFirstField = "startedAt",
    happensSecondField = "completedAt")
@Serializable
data class StepRun(

    @LongValueMin(0)
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
    @TimestampIso8601Format
    @TimestampCoherent
    val startedAt: String,

    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val completedAt: String?,

    /**
     * Execution status of the step, indicating current state.
     */
    val status: ExecutionStatus

) : StandardDto