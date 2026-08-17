package com.wini.owle.dto.steps

import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.provision", ["step"], "/provision")
@Serializable
data class DataPointRead(

//    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
//
//    /**
//     * The step that utilizes this provision.
//     */
//    val step: Step,
//
//    /**
//     * The tool parameter being provisioned.
//     */
//    val toolParam: ToolParam,
//
//    /**
//     * The dataPoints requested by this provision.
//     */
//    @ConstraintSimple([ListMustNotBeEmpty::class])
//    val dataPoints: List<DataPoint>

) : ULongDto