package com.wini.owle.entity.transforms

import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.typing.TypeDeclarationEntity
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
class TransformTargetTypeEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TransformTargetTypeEntity>(TransformTargetTypes) {

        fun buildId(transformationId: Long, typeDeclarationId: Long): CompositeID {
            return CompositeID {
                it[TransformTargetTypes.transformation] = transformationId
                it[TransformTargetTypes.eligibleTypeDeclaration] = typeDeclarationId
            }
        }

        fun create(transformationId: Long, typeDeclarationId: Long): TransformTargetTypeEntity {
            val compositeID = buildId(transformationId, typeDeclarationId)
            val entityID = EntityID(compositeID, TransformTargetTypes)
            return TransformTargetTypeEntity(entityID)
        }
    }

    /**
     * The transformation this target type is associated with.
     */
    var transformation by TransformEntity referencedOn TransformTargetTypes.transformation

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    var eligibleTypeDeclarationEntity by TypeDeclarationEntity referencedOn TransformTargetTypes.eligibleTypeDeclaration

    /**
     * The resulting type after the transformation is applied.
     */
    var resultTypeDeclarationEntity by TypeDeclarationEntity referencedOn TransformTargetTypes
}