package com.wini.owle.entity.runs

import com.wini.owle.entity.user.UserDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.entity.WorkflowExecutions
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
class WorkflowExecutionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowExecutionDao>(WorkflowExecutions)

    /**
     * The workflow being executed in this instance.
     */
    var workflow by WorkflowDao referencedOn WorkflowExecutions.workflow

    /**
     * User initiating and running the workflow execution.
     */
    var user by UserDao referencedOn WorkflowExecutions.user

    /**
     * Date and time the workflow execution started.
     */
    var startedAt by WorkflowExecutions.startedAt

    /**
     * Date and time the workflow execution completed, if applicable.
     */
    var completedAt by WorkflowExecutions.completedAt

    /**
     * Current status of the workflow execution, e.g., running, completed, failed, or cancelled.
     */
    var status by WorkflowExecutions.status
}