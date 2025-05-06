package com.wini.owle.dto.runs.step

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.schema.TimestampCoherent
import com.wini.owle.schema.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.LogLevel
import kotlinx.serialization.Serializable

/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
@OwleModel
@Serializable
data class StepRunLogLine(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Relationship to the associated step execution.
     */
    val stepExecution: StepExecution,

    /**
     * Timestamp for when the log entry was created.
     */
    @TimestampIso8601Format
    @TimestampCoherent
    val timestamp: String,

    /**
     * Level of log severity, ranging from DEBUG to CRITICAL.
     */
    val logLevel: LogLevel,

    /**
     * Message content of the log entry.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_MESSAGE_LENGTH)
    val message: String

) : StandardDto {
    companion object {
        const val MAX_MESSAGE_LENGTH = 16_000
    }
}
