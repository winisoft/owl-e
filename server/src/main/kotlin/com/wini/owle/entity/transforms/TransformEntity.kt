package com.wini.owle.entity.transforms

import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.Transformations
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
class TransformEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TransformEntity>(Transformations)

    /**
     * Name of the transformation.
     */
    var name by Transformations.name

    /**
     * Detailed description of what the transformation does.
     */
    var description by Transformations.description

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    var examples by Transformations.examples

    /**
     * The return type of the transformation, if defined.
     */
    var returnsTypeDeclarationEntity by TypeDeclarationEntity optionalReferencedOn Transformations.returnsTypeDeclaration

    /**
     * The types for which this transformation can be applied
     */
    var eligibleTypeDeclarationsEntity by TypeDeclarationEntity via TransformTargetTypes

}