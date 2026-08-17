package com.wini.owle.dto.transforms

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * Declares that a transform accepts one input type, and what it yields for that input.
 *
 * Holds the related types rather than their raw ids, mirroring the entity.
 */
@ModelDefinition
@ApiEndpointDefinition("api.transforms.transformTargetType", ["transforms"], "/transform-target-type")
@Serializable
data class TransformTargetType(

    /**
     * The transformation this target type is associated with.
     */
    val transform: Transform,

    /**
     * The type this transformation may be applied to.
     */
    val eligibleTypeNode: TypeNode,

    /**
     * The type produced when it is applied to [eligibleTypeNode].
     */
    val returnTypeNode: TypeNode

) : CompositeDto {

    override val owleId: String
        get() = "transform=${transform.owleId},eligibleTypeNode=${eligibleTypeNode.owleId}"
}
