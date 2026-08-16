package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.entity.workflows.DataPointEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Binds one parameter of an inquisition's predicate to the data point that supplies it,
 * optionally through a transform.
 */
class InquisitionDataPointReadEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<InquisitionDataPointReadEntity>(InquisitionDataPointReads)

    var inquisition by InquisitionEntity referencedOn InquisitionDataPointReads.inquisition

    /**
     * The predicate parameter being supplied.
     */
    var callableParameter by CallableParameterEntity referencedOn InquisitionDataPointReads.callableParameter

    /**
     * The data point supplying it.
     */
    var dataPoint by DataPointEntity referencedOn InquisitionDataPointReads.dataPoint

    /**
     * Read-site transform applied to the value on the way in.
     */
    var transformOccurrence by TransformOccurrenceEntity optionalReferencedOn InquisitionDataPointReads.transformOccurrence
}
