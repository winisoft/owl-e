package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
class TransformEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TransformEntity>(Transforms)

    /**
     * Name of the transformation.
     */
    var name by Transforms.name

    /**
     * Detailed description of what the transformation does.
     */
    var description by Transforms.description

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    var examples by Transforms.examples

    /**
     * The types for which this transformation can be applied
     */
    var eligibleTypes by TypeNodeEntity via TransformTargetTypes

}