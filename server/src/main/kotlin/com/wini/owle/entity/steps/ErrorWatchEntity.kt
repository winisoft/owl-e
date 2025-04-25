package com.wini.owle.entity.steps

import com.wini.owle.entity.ErrorWatches
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a watch on specific types of errors that may occur during a step's execution.
 * Specifies the error type and the action to take if the error occurs.
 */
class ErrorWatchEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ErrorWatchEntity>(ErrorWatches)

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
    var onMetStep by StepEntity referencedOn ErrorWatches.onMetStep
}