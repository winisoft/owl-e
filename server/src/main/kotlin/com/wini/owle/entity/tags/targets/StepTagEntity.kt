package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.StepTags
import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a step
 */
class StepTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<StepTagEntity>(StepTags)

    /**
     * The step for which the tag is applied
     */
    var step by StepEntity referencedOn StepTags.step

    /**
     * The tag associated with the step
     */
    var tag by TagEntity referencedOn StepTags.tag
}
