package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
class ToolParamDataPointReadEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<ToolParamDataPointReadEntity>(DataPointReads)

    /**
     * The step that utilizes this provision.
     */
    var step by StepEntity referencedOn DataPointReads.step

    /**
     * The tool parameter being provisioned.
     */
    var toolParam by ToolParamEntity referencedOn DataPointReads.toolParam

    /**
     * The dataPoints requested by this provision.
     */
    var dataPointEntity by DataPointEntity via DataPointWriteLinks
}