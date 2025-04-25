package com.wini.owle.entity.runs.step

import com.wini.owle.entity.ExecutionLogs
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
class StepRunLogLineEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<StepRunLogLineEntity>(ExecutionLogs)

    /**
     * Relationship to the associated step execution.
     */
    var stepExecution by StepRunEntity referencedOn ExecutionLogs.stepExecution

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