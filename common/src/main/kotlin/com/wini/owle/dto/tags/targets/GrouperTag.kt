package com.wini.owle.dto.tags.targets

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.tags.Tag
import com.wini.owle.dto.workflows.Grouper
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * A tag applied to a grouper.
 */
@ModelDefinition
@Serializable
data class GrouperTag(

    val grouper: Grouper,

    val tag: Tag,

) : CompositeDto {

    override val owleId: String get() = "grouper=${grouper.owleId},tag=${tag.owleId}"
}
