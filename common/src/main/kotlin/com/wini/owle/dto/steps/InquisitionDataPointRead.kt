package com.wini.owle.dto.steps

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * Binds one parameter of an inquisition's predicate to the data point supplying it.
 */
@ModelDefinition
@Serializable
data class InquisitionDataPointRead(

    val inquisition: Inquisition,

    val callableParameter: CallableParameter,

    val dataPoint: DataPoint,

    val transformOccurrence: TransformOccurrence?,

) : CompositeDto {

    override val owleId: String get() = "inquisition=${inquisition.owleId},callableParameter=${callableParameter.owleId}"
}
