package com.wini.owle.dto.tags

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A parent/child edge in the tag hierarchy.
 *
 * Carries the related [Tag]s rather than their raw ids: the entity side holds real references,
 * and matching that here is what lets the mapping be derived instead of hand-written.
 */
@ModelDefinition
@ApiEndpointDefinition("api.user.tagRelation", ["user"], "/tag-relation")
@Serializable
data class TagRelation(

    val parentTag: Tag,

    val childTag: Tag

) : CompositeDto {

    override val owleId: String get() = "parentTag=${parentTag.owleId},childTag=${childTag.owleId}"
}
