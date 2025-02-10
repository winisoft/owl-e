package com.wini.owle.entity.transforms

import com.wini.owle.entity.TransformTargetTypes
import com.wini.owle.entity.typing.TypeDeclarationDao
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
class TransformTargetTypeDao(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<TransformTargetTypeDao>(TransformTargetTypes) {

        fun create(transformationId: Long, typeDeclarationId: Long): TransformTargetTypeDao {
            val compositeID = CompositeID {
                it[TransformTargetTypes.transformation] = transformationId
                it[TransformTargetTypes.eligibleTypeDeclaration] = typeDeclarationId
            }
            val entityID = EntityID(compositeID, TransformTargetTypes)
            return TransformTargetTypeDao(entityID)
        }
    }

    /**
     * The transformation this target type is associated with.
     */
    var transformation by TransformationDao referencedOn TransformTargetTypes.transformation

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    var eligibleTypeDeclaration by TypeDeclarationDao referencedOn TransformTargetTypes.eligibleTypeDeclaration

    /**
     * The resulting type after the transformation is applied.
     */
    var resultTypeDeclaration by TypeDeclarationDao referencedOn TransformTargetTypes
}