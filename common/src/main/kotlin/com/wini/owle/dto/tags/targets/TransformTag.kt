package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.transforms.Transform
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a transform.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.transformTag", ["user"], "/transform-tag")
@Serializable
data class TransformTag(

    val transform: Transform,

    val tag: Tag

) : CompositeDto {

    override val owleId: String get() = "transform=${transform.owleId},tag=${tag.owleId}"
}
