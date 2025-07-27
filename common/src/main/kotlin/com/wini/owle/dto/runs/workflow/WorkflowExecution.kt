package com.wini.owle.dto.runs.workflow

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.runs.step.StepRun
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.TimestampFormatError
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
@Serializable
@ModelDefinition
data class WorkflowExecution(

    @ConstraintSimple([PositiveValueLongError::class])
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
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val startedAt: String,

    /**
     * Date and time the workflow execution completed, if applicable.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val completedAt: String?,

    /**
     * Current status of the workflow execution, e.g., running, completed, failed, or cancelled.
     */
    val status: ExecutionStatus,

    val stepRuns: List<StepRun>

) : StandardDto