package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents an overwrite made to a data point's value following the successful execution of a step.
 * Tracks the affected data point, the responsible step, and optionally, any transformation.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.dataPointWrite", ["step"], "/datapoint-write")
@Serializable
data class DataPointWrite(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * The data point affected by this revision.
     */
    val dataPoint: DataPoint,

    /**
     * The step responsible for this revision.
     */
    val step: Step,

    /**
     * A potentially configured application of a transform that has been applied to modify its value
     */
    val transformOccurrence: TransformOccurrence?

) : ULongDto