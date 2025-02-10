package com.wini.owle.entity.runs

import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.StepExecutions
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
class StepExecutionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepExecutionDao>(StepExecutions)

    /**
     * Relationship to the associated workflow execution.
     */
    var workflowExecution by WorkflowExecutionDao referencedOn StepExecutions.workflowExecution

    /**
     * Relationship to the step associated with this execution.
     */
    var step by StepDao referencedOn StepExecutions.step

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