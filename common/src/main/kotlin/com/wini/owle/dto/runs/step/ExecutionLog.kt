package com.wini.owle.dto.runs.step

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.ExecutionEventKind
import com.wini.owle.dto.runs.enums.ExecutionLogLevel
import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * One event in a run's audit trail - the record the containment guarantee rests on.
 */
@ModelDefinition
@Serializable
data class ExecutionLog(

    override val id: ULong,

    val workflowExecution: WorkflowRun,

    /** Monotonic position within the run; unique per run. */
    val seq: ULong,

    val loggedAt: String,

    val eventKind: ExecutionEventKind,

    val logLevel: ExecutionLogLevel,

    val workflow: Workflow?,

    val step: Step?,

    val inquisition: Inquisition?,

    val dataPoint: DataPoint?,

    val detail: String?,

    val message: String?,

) : ULongDto
