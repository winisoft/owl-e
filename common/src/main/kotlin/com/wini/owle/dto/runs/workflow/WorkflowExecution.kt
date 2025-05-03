package com.wini.owle.dto.runs.workflow

import com.wini.owle.ksp.CompletedTimeBeforeStart
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.TimestampCoherent
import com.wini.owle.ksp.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.step.StepExecution
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import kotlinx.serialization.Serializable


/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
@OwleModel
@CompletedTimeBeforeStart("startedAt", "completedAt")
@Serializable
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

    val stepExecutions: List<StepExecution>

) : StandardDto