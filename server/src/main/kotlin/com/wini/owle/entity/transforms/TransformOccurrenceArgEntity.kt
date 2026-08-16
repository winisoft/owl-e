package com.wini.owle.entity.transforms

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * The value bound to one of a transform's declared arguments, at one application site.
 */
class TransformOccurrenceArgEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformOccurrenceArgEntity>(TransformOccurrenceArgs)

    var transformOccurrence by TransformOccurrenceEntity referencedOn TransformOccurrenceArgs.transformOccurrence

    var transformArg by TransformArgEntity referencedOn TransformOccurrenceArgs.transformArg

    var value by TransformOccurrenceArgs.value
}
