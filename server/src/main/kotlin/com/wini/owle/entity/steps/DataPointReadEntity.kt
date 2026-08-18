package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.typing.CallableParameterEntity
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Supplies one parameter of a callable from a data point, optionally through a transform.
 *
 * Exactly one of [step] or [inquisition] owns the read - a database CHECK enforces it. Keeping
 * both owners in one table means a further cause of a read is another discriminated case rather
 * than another table.
 */
class DataPointReadEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<DataPointReadEntity>(DataPointReads)

    /** Set when this read supplies a step's tool. */
    var step by StepEntity optionalReferencedOn DataPointReads.step

    /** Set when this read supplies an inquisition's predicate. */
    var inquisition by InquisitionEntity optionalReferencedOn DataPointReads.inquisition

    var callableParameter by CallableParameterEntity referencedOn DataPointReads.callableParameter

    var dataPoint by DataPointEntity referencedOn DataPointReads.dataPoint

    /** Read-site transform applied on the way in. */
    var transformOccurrence by TransformOccurrenceEntity optionalReferencedOn DataPointReads.transformOccurrence
}
