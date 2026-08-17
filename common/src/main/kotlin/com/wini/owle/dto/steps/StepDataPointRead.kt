package com.wini.owle.dto.steps

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.typing.CallableParameter
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * Supplies one parameter of a step's tool from a data point.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.stepDataPointRead", ["step"], "/step-datapoint-read")
@Serializable
data class StepDataPointRead(

    val step: Step,

    val callableParameter: CallableParameter,

    val dataPoint: DataPoint,

    val transformOccurrence: TransformOccurrence?,

) : CompositeDto {

    override val owleId: String get() = "step=${step.owleId},callableParameter=${callableParameter.owleId}"
}
