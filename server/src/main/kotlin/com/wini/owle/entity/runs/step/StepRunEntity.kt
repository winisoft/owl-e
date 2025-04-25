package com.wini.owle.entity.runs.step

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.StepExecutions
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
class StepRunEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepRunEntity>(StepExecutions)

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowRunEntity referencedOn StepExecutions.workflowExecution

    /**
     * Relationship to the step associated with this execution.
     */
    var step by StepEntity referencedOn StepExecutions.step

    /**
     * Timestamp for when the step execution started.
     */
    var startedAt by StepExecutions.startedAt

    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    var completedAt by StepExecutions.completedAt

    /**
     * Execution status of the step, indicating current state.
     */
    var status by StepExecutions.status
}