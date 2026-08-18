package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.workflows.DataPoint
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

/**
 * Represents a conditional check or "inquisition" within a workflow,
 * analyzing a specific data point value and determining if workflow redirection is required.
 */
@ModelDefinition
@ApiEndpointDefinition("api.steps.inquisition", ["step"], "/inquisition")
@Serializable
data class Inquisition(

    override val id: ULong,

    /**
     * The workflow step that includes this inquisition as a conditional check.
     */
    val step: Step,

    /**
     * Determines the order in which this inquisition is executed within its associated workflow step.
     */
        val executionOrder: ULong

) : ULongDto