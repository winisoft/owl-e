package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents the target types for a transformation.
 * Each target type defines which types are eligible for a transformation and the resulting type after the transformation is applied.
 */
@ModelDefinition
@ApiEndpointDefinition("api.transforms.transformTargetType", ["transforms"], "/transform-target-type")
@Serializable
data class TransformTargetType(

    /**
     * The transformation this target type is associated with.
     */
    @ConstraintSimple([PositiveValueULongError::class])
    val transformationId: ULong,

    /**
     * The type declaration representing the eligible type for this transformation.
     */
    @ConstraintSimple([PositiveValueULongError::class])
    val typeDeclarationId: ULong

) : CompositeDto {

    override val owleId: String get() = "transformationId=$transformationId,typeDeclarationId=$typeDeclarationId"

}