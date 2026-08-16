package com.wini.owle.entity.steps

import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * One positional argument to a write-site transform.
 */
class DataPointWriteArgEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<DataPointWriteArgEntity>(DataPointWriteArgs)

    var dataPointWrite by DataPointWriteEntity referencedOn DataPointWriteArgs.dataPointWrite

    var dataPoint by DataPointEntity referencedOn DataPointWriteArgs.dataPoint

    var position by DataPointWriteArgs.position
}
