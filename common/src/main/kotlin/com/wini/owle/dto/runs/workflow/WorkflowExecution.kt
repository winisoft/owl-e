package com.wini.owle.dto.runs.workflow

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.step.StepRun
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.PreventsTimeParadoxes
import kotlinx.serialization.Serializable


/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
@OwleModel
@Serializable
@PreventsTimeParadoxes(
    "startedAt",
    "completedAt")
data class WorkflowExecution(

    @LongValueMin(0)
    override val id: Long,

    /**
     * The workflow being executed in this instance.
     */
    val workflow: Workflow,

    /**
     * User initiating and running the workflow execution.
     */
    val user: User,

    /**
     * Date and time the workflow execution started.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val startedAt: String,

    /**
     * Date and time the workflow execution completed, if applicable.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val completedAt: String?,

    /**
     * Current status of the workflow execution, e.g., running, completed, failed, or cancelled.
     */
    val status: ExecutionStatus,

    val stepRuns: List<StepRun>

) : StandardDto