package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.TransformTags
import com.wini.owle.entity.transforms.TransformEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a transform
 */
class TransformTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<TransformTagEntity>(TransformTags)

    /**
     * The transform for which the tag is applied
     */
    var transform by TransformEntity referencedOn TransformTags.transform

    /**
     * The tag associated with the transform
     */
    var tagEntity by TagEntity.Companion referencedOn TransformTags.tag

}