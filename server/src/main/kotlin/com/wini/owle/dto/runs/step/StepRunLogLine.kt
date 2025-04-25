package com.wini.owle.dto.runs.step

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.LogLevel
import kotlinx.serialization.Serializable

/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
@Serializable
data class StepRunLogLine(
    override val id: Long,
    /**
     * Relationship to the associated step execution.
     */
    val stepExecution: StepExecution,
    /**
     * Timestamp for when the log entry was created.
     */
    val timestamp: String,
    /**
     * Level of log severity, ranging from DEBUG to CRITICAL.
     */
    val logLevel: LogLevel,
    /**
     * Message content of the log entry.
     */
    val message: String
) : StandardDto