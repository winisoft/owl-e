package com.wini.owle.entity.runs.workflow

import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.WorkflowExecutions
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents an execution instance of a workflow. Tracks the execution details such as start and end times,
 * user executing the workflow, and current status of the workflow execution.
 */
class WorkflowRunEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowRunEntity>(WorkflowExecutions)

    /**
     * The workflow being executed in this instance.
     */
    var workflow by WorkflowEntity referencedOn WorkflowExecutions.workflow

    /**
     * User initiating and running the workflow execution.
     */
    var userEntity by UserEntity referencedOn WorkflowExecutions.user

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