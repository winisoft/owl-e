package com.wini.owle.entity.typing

import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


class TypeNodeRelationEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TypeNodeRelationEntity>(TypeNodeRelations)
//    {
//        fun buildId(parentNodeId: ULong, childNodeId: ULong) : CompositeID {
//            return CompositeID {
//                it[ClassInheritances.parentNode] = parentNodeId
//                it[ClassInheritances.childNode] = childNodeId
//            }
//        }
//        fun create(parentNodeId: ULong, childNodeId: ULong): TransformTargetTypeEntity {
//            val compositeID = TypeVarBoundEntity.buildId(parentNodeId,childNodeId )
//            val entityID = EntityID(compositeID, TransformTargetTypes)
//            return TransformTargetTypeEntity(entityID)
//        }
//    }

    /**
     * Represents the parent node in an inheritance relationship. Both it and parentNode are CodeFeatureKind.BASE
     */
    var parentNode by TypeNodeEntity referencedOn TypeNodeRelations.parentNode

    /**
     * Represents the child node in an inheritance relationship. Both it and parentNode are CodeFeatureKind.BASE
     */
    var childNode by TypeNodeEntity referencedOn TypeNodeRelations.childNode

}