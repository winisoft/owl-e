package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.PrereqInjections
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the prerequisite data injection between workflows.
 * Specifies how `sourceDataPoint` in the completed workflow replaces `injectedDataPoint` in the target workflow.
 */
class InitialDataPointInjectorEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<InitialDataPointInjectorEntity>(PrereqInjections)

    /**
     * Relationship to the workflow transition junction.
     */
    var junction by WorkflowJunctionEntity referencedOn PrereqInjections.junction

    /**
     * Relationship to the source data point in the origin workflow.
     */
    var sourceDataPointEntity by DataPointEntity referencedOn PrereqInjections.sourceDataPoint

    /**
     * Relationship to the injected data point in the target workflow.
     */
    var injectedDataPointEntity by DataPointEntity referencedOn PrereqInjections.injectedDataPoint

    /**
     * Optional name for describing the injection.
     */
    var name by PrereqInjections.name

    /**
     * Optional description for additional details on the injection.
     */
    var description by PrereqInjections.description
}