package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
@ModelDefinition
@Serializable
data class TransformTargetType(

    /**
     * The transformation this target type is associated with.
     */
    @ConstraintSimple([PositiveValueLongError::class])
    val transformationId: Long,

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    @ConstraintSimple([PositiveValueLongError::class])
    val typeDeclarationId: Long

) : CompositeDto {

    override fun idToString(): String {
        return "transformationId=$transformationId,typeDeclarationId=$typeDeclarationId"
    }

}