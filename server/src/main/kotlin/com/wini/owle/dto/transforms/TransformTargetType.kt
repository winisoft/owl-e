package com.wini.owle.dto.transforms

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
@Serializable
data class TransformTargetType(
    /**
     * The transformation this target type is associated with.
     */
    val transformationId: Long,
    /**
     * The type declaration representing the eligible type for this transformation.
     */
    val typeDeclarationId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "transformationId={$transformationId}&typeDeclarationId={$typeDeclarationId}"
    }

}