package com.wini.owle.entity.steps

import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.ToolParamDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the parameters and data provisions associated with a tool execution within a step.
 * Links the tool parameter to the step and holds any associated data points.
 */
class ProvisionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProvisionDao>(Provisions)

    /**
     * The step that utilizes this provision.
     */
    var step by StepDao referencedOn Provisions.step

    /**
     * The tool parameter being provisioned.
     */
    var toolParam by ToolParamDao referencedOn Provisions.toolParam

    /**
     * The dataPoints requested by this provision.
     */
    var dataPoint by DataPointDao via ProvisionDataPoints
}