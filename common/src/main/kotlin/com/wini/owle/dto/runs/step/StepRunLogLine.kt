package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import kotlinx.serialization.Serializable

/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
@ModelDefinition
@Serializable
data class StepRunLogLine(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Relationship to the associated step execution.
     */
    val stepRun: StepRun,

    /**
     * Timestamp for when the log entry was created.
     */
    @ConstraintSimple([TimestampFormatError::class, IncoherentInstantError::class])
    val timestamp: String,

    /**
     * Level of log severity, ranging from DEBUG to CRITICAL.
     */
    val logLevel: LogLevel,

    /**
     * Message content of the log entry.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_MESSAGE_LENGTH)
    val message: String

) : StandardDto {
    companion object {
        const val MAX_MESSAGE_LENGTH = 16_000
    }
}
