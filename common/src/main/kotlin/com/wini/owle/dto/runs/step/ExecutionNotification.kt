package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.error.IncoherentInstantError
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.error.TimestampFormatError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Stores individual log messages generated during step execution.
 * Each log entry includes a timestamp, log level, and message content.
 */
@ModelDefinition
@Serializable
data class ExecutionNotification(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Relationship to the associated step execution.
     */
    val stepTestRun: StepTestRun,

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
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16000)
    val message: String

) : ULongDto
