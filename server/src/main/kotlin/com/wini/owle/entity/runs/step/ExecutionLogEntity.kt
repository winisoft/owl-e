package com.wini.owle.entity.runs.step

import com.wini.owle.entity.runs.ExecutionLogs
import com.wini.owle.entity.runs.workflow.WorkflowExecutionEntity
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * One event in a run's audit trail.
 *
 * This is where the containment guarantee is cashed out: no outcome foreshadowed by a workflow's
 * configuration may occur without a line here articulating everything known about it. [eventKind]
 * names the lifecycle event; the nullable [workflow]/[step]/[inquisition]/[dataPoint] columns
 * pin it to whichever configuration elements were in play; [detail] carries the JSON payload.
 *
 * [seq] is unique per run and gives a total order independent of clock resolution.
 */
class ExecutionLogEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ExecutionLogEntity>(ExecutionLogs)

    /**
     * The run this event belongs to.
     */
    var workflowExecution by WorkflowExecutionEntity referencedOn ExecutionLogs.workflowExecution

    /**
     * Monotonic position within the run. Unique per run.
     */
    var seq by ExecutionLogs.seq

    /**
     * Wall-clock time, to milliseconds.
     */
    var loggedAt by ExecutionLogs.loggedAt

    /**
     * Which lifecycle event this is.
     */
    var eventKind by ExecutionLogs.eventKind

    /**
     * Severity.
     */
    var logLevel by ExecutionLogs.logLevel

    /**
     * The workflow in play, for events that have one.
     */
    var workflow by WorkflowEntity optionalReferencedOn ExecutionLogs.workflow

    /**
     * The step in play, for events that have one.
     */
    var step by StepEntity optionalReferencedOn ExecutionLogs.step

    /**
     * The inquisition in play, for routing events.
     */
    var inquisition by InquisitionEntity optionalReferencedOn ExecutionLogs.inquisition

    /**
     * The data point in play, for read/write events.
     */
    var dataPoint by DataPointEntity optionalReferencedOn ExecutionLogs.dataPoint

    /**
     * Structured JSON payload — resolved argument values, predicate outcome, chosen route.
     */
    var detail by ExecutionLogs.detail

    /**
     * Human-readable rendering of the event.
     */
    var message by ExecutionLogs.message
}
