package com.wini.owle.entity.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.steps.ToolParamEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
class ToolParamDataPointEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ToolParamDataPointEntity>(Provisions)

    /**
     * The step that utilizes this provision.
     */
    var step by StepEntity referencedOn Provisions.step

    /**
     * The tool parameter being provisioned.
     */
    var toolParam by ToolParamEntity referencedOn Provisions.toolParam

    /**
     * The dataPoints requested by this provision.
     */
    var dataPointEntity by DataPointEntity via ProvisionDataPoints
}