package com.wini.owle.entity.runs.step

import com.wini.owle.entity.runs.ExecutionLogs
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
class ExecutionNotificationEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<ExecutionNotificationEntity>(ExecutionLogs)

    /**
     * Relationship to the associated step execution.
     */
    var stepExecution by StepTestRunEntity referencedOn ExecutionLogs.stepExecution

    /**
     * Timestamp for when the log entry was created.
     */
    var timestamp by ExecutionLogs.timestamp

    /**
     * Level of log severity, ranging from DEBUG to CRITICAL.
     */
    var logLevel by ExecutionLogs.logLevel

    /**
     * Message content of the log entry.
     */
    var message by ExecutionLogs.message
}