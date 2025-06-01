package com.wini.owle.entity.transforms

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class TransformOccurrenceArgEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformOccurrenceArgEntity>(TransformOccurrenceArgs)

    var transformOccurrenceEntity by TransformOccurrenceEntity referencedOn TransformOccurrenceArgs.transformArg

    var transformationArg by TransformArgEntity referencedOn TransformOccurrenceArgs.transformOccurrence

    var value by TransformOccurrenceArgs.value

}