package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
class TransformTargetTypeEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TransformTargetTypeEntity>(TransformTargetTypes) {

        fun buildId(transformId: ULong, eligibleTypeNodeId: ULong): CompositeID {
            return CompositeID {
                it[TransformTargetTypes.transform] = transformId
                it[TransformTargetTypes.eligibleTypeNode] = eligibleTypeNodeId
            }
        }

        fun create(transformId: ULong, eligibleTypeNodeId: ULong): TransformTargetTypeEntity {
            val compositeID = buildId(transformId, eligibleTypeNodeId)
            val entityID = EntityID(compositeID, TransformTargetTypes)
            return TransformTargetTypeEntity(entityID)
        }
    }

    /**
     * The transformation this target type is associated with.
     */
    var transform by TransformEntity referencedOn TransformTargetTypes.transform

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    var eligibleTypeNode by TypeNodeEntity referencedOn TransformTargetTypes.eligibleTypeNode

    /**
     * The resulting type after the transformation is applied.
     */
    var returnTypeNode by TypeNodeEntity referencedOn TransformTargetTypes.returnTypeNode
}
