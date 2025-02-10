package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.PrereqInjections
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the prerequisite data injection between workflows.
 * Specifies how `sourceDataPoint` in the completed workflow replaces `injectedDataPoint` in the target workflow.
 */
class PreReqInjectionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PreReqInjectionDao>(PrereqInjections)

    /**
     * Relationship to the workflow transition junction.
     */
    var junction by WorkflowJunctionDao referencedOn PrereqInjections.junction

    /**
     * Relationship to the source data point in the origin workflow.
     */
    var sourceDataPoint by DataPointDao referencedOn PrereqInjections.sourceDataPoint

    /**
     * Relationship to the injected data point in the target workflow.
     */
    var injectedDataPoint by DataPointDao referencedOn PrereqInjections.injectedDataPoint

    /**
     * Optional name for describing the injection.
     */
    var name by PrereqInjections.name

    /**
     * Optional description for additional details on the injection.
     */
    var description by PrereqInjections.description
}