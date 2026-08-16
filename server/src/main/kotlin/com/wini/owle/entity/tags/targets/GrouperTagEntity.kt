package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.GrouperTags
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.workflows.GrouperEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a grouper.
 */
class GrouperTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<GrouperTagEntity>(GrouperTags)

    /**
     * The grouper for which the tag is applied
     */
    var grouper by GrouperEntity referencedOn GrouperTags.grouper

    /**
     * The tag associated with the grouper
     */
    var tag by TagEntity referencedOn GrouperTags.tag
}
