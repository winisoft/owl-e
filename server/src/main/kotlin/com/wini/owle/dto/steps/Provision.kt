package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.ToolParam
import kotlinx.serialization.Serializable

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
@Serializable
data class Provision(
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
    val dataPoints: List<DataPoint>
) : StandardDto