package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.error.ListMustNotBeEmpty
import com.wini.owle.error.PositiveValueLongError
import kotlinx.serialization.Serializable

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
@ModelDefinition
@Serializable
data class Provision(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * The step that utilizes this provision.
     */
    val step: Step,

    /**
     * The tool parameter being provisioned.
     */
    val toolParam: ToolParam,

    /**
     * The dataPoints requested by this provision.
     */
    @ConstraintSimple([ListMustNotBeEmpty::class])
    val dataPoints: List<DataPoint>

) : StandardDto