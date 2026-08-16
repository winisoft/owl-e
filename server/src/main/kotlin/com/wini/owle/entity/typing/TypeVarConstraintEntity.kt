package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * One admissible type for a constrained TypeVar — `TypeVar("T", int, str)` yields one row per constraint.
 */
class TypeVarConstraintEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeVarConstraintEntity>(TypeVarConstraints)

    /**
     * The TypeVar's own TypeNode
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarConstraints.typeVarNode

    /**
     * A TypeNode reference to the constraint, e.g. 'int' or 'str'
     */
    var constraintNode by TypeNodeEntity referencedOn TypeVarConstraints.constraintNode
}
