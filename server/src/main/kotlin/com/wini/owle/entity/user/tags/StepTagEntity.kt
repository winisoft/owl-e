package com.wini.owle.entity.user.tags

import com.wini.owle.entity.StepTags
import com.wini.owle.entity.workflows.StepEntity
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a step
 */
class StepTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<StepTagEntity>(StepTags) {

    }

    /**
     * The workflow for which the tag is applied
     */
    var step by StepEntity referencedOn StepTags.step

    /**
     * The tag associated with the step
     */
    var tagEntity by TagEntity referencedOn StepTags.tag
}