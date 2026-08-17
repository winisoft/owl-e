package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents the prerequisite data injection between workflows.
 * Specifies how `sourceDataPoint` in the completed workflow replaces `injectedDataPoint` in the target workflow.
 */
class DataPointInjectionEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<DataPointInjectionEntity>(DataPointInjections)

    /**
     * Relationship to the workflow transition junction.
     */
    var junction by WorkflowJunctionEntity referencedOn DataPointInjections.junction

    /**
     * Relationship to the source data point in the origin workflow.
     */
    var sourceDataPoint by DataPointEntity referencedOn DataPointInjections.sourceDataPoint

    /**
     * Relationship to the injected data point in the target workflow.
     */
    var injectedDataPoint by DataPointEntity referencedOn DataPointInjections.targetDataPoint

    /**
     * Optional name for describing the injection.
     */
    var name by DataPointInjections.name

    /**
     * Optional description for additional details on the injection.
     */
    var description by DataPointInjections.description
}