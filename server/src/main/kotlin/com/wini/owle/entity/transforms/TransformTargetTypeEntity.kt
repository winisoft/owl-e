package com.wini.owle.entity.transforms

import com.wini.owle.entity.typing.TypeNodeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
class TransformTargetTypeEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TransformTargetTypeEntity>(TransformTargetTypes) {

        fun buildId(transformationId: ULong, typeDeclarationId: ULong): CompositeID {
            return CompositeID {
                it[TransformTargetTypes.transform] = transformationId
                it[TransformTargetTypes.eligibleType] = typeDeclarationId
            }
        }

        fun create(transformationId: ULong, typeDeclarationId: ULong): TransformTargetTypeEntity {
            val compositeID = buildId(transformationId, typeDeclarationId)
            val entityID = EntityID(compositeID, TransformTargetTypes)
            return TransformTargetTypeEntity(entityID)
        }
    }

    /**
     * The transformation this target type is associated with.
     */
    var transformation by TransformEntity referencedOn TransformTargetTypes.transform

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    var eligibleTypeNode by TypeNodeEntity referencedOn TransformTargetTypes.eligibleType

    /**
     * The resulting type after the transformation is applied.
     */
    var resultTypeNode by TypeNodeEntity referencedOn TransformTargetTypes
}