package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.schema.error.TimestampCoherent
import com.wini.owle.schema.error.TimestampIso8601Format
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.error.StringNotEmpty
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
    val stepRun: StepRun,

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
