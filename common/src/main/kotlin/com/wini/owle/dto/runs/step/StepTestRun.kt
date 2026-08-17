package com.wini.owle.dto.runs.step

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.TimestampMustBeChronologicallyCoherent
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.TimestampFormatMustMatchIso8601
import com.wini.owle.schema.error.PreventsTimeParadox
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents the execution instance of a step within a workflow execution.
 * Tracks the status, start and completion times, and links to execution logs.
 */
@ModelDefinition
@ApiEndpointDefinition("api.runs.step.stepRun", ["runs"], "/step-run")
@PreventsTimeParadox(
    happensFirstField = "started",
    happensSecondField = "completed")
@Serializable
data class StepTestRun(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Relationship to the step associated with this execution.
     */
    val step: Step,

    /**
     * Timestamp for when the step execution started.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val started: String? = null,

    /**
     * Timestamp for when the step execution completed, if applicable.
     */
    @ConstraintSimple([TimestampFormatMustMatchIso8601::class, TimestampMustBeChronologicallyCoherent::class])
    val completed: String? = null,

    /**
     * Execution status of the step, indicating current state.
     */
    val status: ExecutionStatus

) : ULongDto