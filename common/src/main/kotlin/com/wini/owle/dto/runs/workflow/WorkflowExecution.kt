package com.wini.owle.dto.runs.workflow

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
@Serializable
@ModelDefinition
@ApiEndpointDefinition("api.runs.workflow.workflowExecution", ["executions"], "/workflow-execution")
data class WorkflowExecution(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * The workflow being executed in this instance.
     */
    val workflow: Workflow,

    /**
     * Date and time the workflow execution started.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val startedAt: String,

    /**
     * Date and time the workflow execution completed, if applicable.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val completedAt: String?,

    /**
     * Current status of the workflow execution, e.g., running, completed, failed, or cancelled.
     */
    val status: ExecutionStatus,
) : ULongDto