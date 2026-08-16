package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass


/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 */
class TypeVarBoundEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TypeVarBoundEntity>(TypeVarBounds)

    /**
     * The TypeNode that is the TypeVar itself
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarBounds.typeVarNode

    /**
     * The TypeNode representing the upper bound
     */
    var boundTypeNode by TypeNodeEntity referencedOn TypeVarBounds.boundNode
}
