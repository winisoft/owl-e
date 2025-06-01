package com.wini.owle.entity.steps

import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.transforms.TransformOccurrences
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a revision made to a data point following the successful execution of a step.
 * Tracks the affected data point, the responsible step, and optionally, any transformation.
 */
class DataPointWriteEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<DataPointWriteEntity>(DataPointWrites)

    /**
     * The data point affected by this revision.
     */
    var dataPoint by DataPointEntity referencedOn DataPointWrites.dataPoint

    /**
     * The step responsible for this revision.
     */
    var step by StepEntity referencedOn DataPointWrites.step

    var transformOccurrence by TransformOccurrenceEntity optionalReferencedOn TransformOccurrences.transformation

}