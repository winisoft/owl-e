package com.wini.owle.entity.typing

import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.transforms.TransformTargetTypes
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 */
class TypeVarBoundEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeVarBoundEntity>(TypeVarBounds) {
        fun buildId(typeVarNodeId: ULong, boundTypeNodeId: ULong) : CompositeID {
            return CompositeID {
                it[TypeVarBounds.typeVarNode] = typeVarNodeId
                it[TypeVarBounds.boundNode] = boundTypeNodeId
            }
        }
        fun create(typeVarNodeId: ULong, boundTypeNodeId: ULong): TypeVarBoundEntity {
            val compositeID = buildId(typeVarNodeId, boundTypeNodeId)
            val entityID = EntityID(compositeID, TypeVarBounds)
            return TypeVarBoundEntity(entityID)
        }
    }

    /**
     * The TypeNode that is the TypeVar itself
     */
    var typeVarNode by TypeNodeEntity referencedOn TypeVarBounds.typeVarNode

    /**
     * The TypeNode representing the upper bound
     */
    var boundTypeNode by TypeNodeEntity referencedOn TypeVarBounds.boundNode


}