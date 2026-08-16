package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Represents a watch on specific types of errors that may occur during a step's execution.
 * Specifies the error type and the action to take if the error occurs.
 */
class ErrorWatchEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ErrorWatchEntity>(ErrorWatches)

    /**
     * Step within the workflow where this error watch is active.
     */
    var step by StepEntity referencedOn ErrorWatches.step

    /**
     * Type of error being monitored (e.g., a specific exception type).
     */
    var errorType by ErrorWatches.errorType

    /**
     * Workflow step to execute if this error condition is met.
     */
    var triggerStep by StepEntity referencedOn ErrorWatches.triggerStep

    /**
     * The workflow the trigger step must belong to — enforced by a composite FK.
     */
    var workflow by WorkflowEntity referencedOn ErrorWatches.workflow

    /**
     * Ordered call-stack refinements that narrow when this watch fires.
     */
    val functions: SizedIterable<ErrorWatchFnEntity>
        get() = ErrorWatchFnEntity
            .find { ErrorWatchFns.errorWatch eq id }
            .orderBy(ErrorWatchFns.position to SortOrder.ASC)
}
