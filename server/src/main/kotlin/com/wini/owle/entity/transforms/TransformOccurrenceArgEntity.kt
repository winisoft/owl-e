package com.wini.owle.entity.transforms

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import com.wini.owle.dto.transforms.enums.TransformArgSource
import com.wini.owle.entity.workflows.DataPointEntity
import com.wini.owle.entity.workflows.StepEntity


/**
 * The value bound to one of a transform's declared arguments, at one application site.
 */
class TransformOccurrenceArgEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformOccurrenceArgEntity>(TransformOccurrenceArgs)

    var transformOccurrence by TransformOccurrenceEntity referencedOn TransformOccurrenceArgs.transformOccurrence

    var transformArg by TransformArgEntity referencedOn TransformOccurrenceArgs.transformArg

    /** Which source below holds the value. See [TransformArgSource]. */
    var sourceKind by TransformOccurrenceArgs.sourceKind

    /** The literal, when [sourceKind] is [TransformArgSource.LITERAL]. */
    var value by TransformOccurrenceArgs.value

    var sourceDataPoint by DataPointEntity optionalReferencedOn TransformOccurrenceArgs.sourceDataPoint

    var sourceStep by StepEntity optionalReferencedOn TransformOccurrenceArgs.sourceStep

    var sourceOccurrence by TransformOccurrenceEntity optionalReferencedOn TransformOccurrenceArgs.sourceOccurrence
}
