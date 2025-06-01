package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents an instance of an applied transformation.
 * Each applied transformation has a type, may recursively reference a previous applied transform,
 * and specifies the result type it produces.
 */
class TransformOccurrenceEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<TransformOccurrenceEntity>(TransformOccurrences)

    /**
     * The transformation from which this applied instance originates.
     */
    var transformation by TransformEntity referencedOn TransformOccurrences.transformation

    /**
     * Describes the type of occurrence for the transformation (e.g., 'provision', 'revision').
     */
    var occurrence by TransformOccurrences.occurrence

    /**
     * The type of the result produced by this transformation.
     */
    var resultType by TypeNodeEntity referencedOn TransformOccurrences.occurrence


}