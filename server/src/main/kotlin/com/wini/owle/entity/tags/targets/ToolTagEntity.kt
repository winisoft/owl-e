package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.steps.ToolEntity
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.ToolTags
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a tool
 */
class ToolTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<ToolTagEntity>(ToolTags)

    /**
     * The tool for which the tag is applied
     */
    var tool by ToolEntity referencedOn ToolTags.tool

    /**
     * The tag associated with the tool
     */
    var tag by TagEntity referencedOn ToolTags.tag
}
