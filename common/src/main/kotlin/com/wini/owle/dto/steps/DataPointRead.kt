package com.wini.owle.dto.steps

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * Supplies one parameter of a callable from a data point, optionally through a transform.
 *
 * Exactly one of [step] or [inquisition] owns the read; the database enforces it with a CHECK.
 * The reason a transform runs is carried by the transform occurrence's own initiator, so a
 * further cause of a read is a discriminated case here rather than another table.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.dataPointRead", ["step"], "/data-point-read")
@Serializable
data class DataPointRead(

    override val id: ULong,

    /** Set when this read supplies a step's tool. */
    val step: Step?,

    /** Set when this read supplies an inquisition's predicate. */
    val inquisition: Inquisition?,

    val callableParameter: CallableParameter,

    val dataPoint: DataPoint,

    val transformOccurrence: TransformOccurrence?

) : ULongDto
