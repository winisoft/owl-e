package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.api.ApiEndpointDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a tool.
 */
@ModelDefinition
@ApiEndpointDefinition("api.tags.toolTag", ["user"], "/tool-tag")
@Serializable
data class ToolTag(

    val tool: Tool,

    val tag: Tag

) : CompositeDto {

    override val owleId: String get() = "tool=${tool.owleId},tag=${tag.owleId}"
}
