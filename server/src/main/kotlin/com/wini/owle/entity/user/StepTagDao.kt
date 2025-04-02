package com.wini.owle.entity.user

import com.wini.owle.entity.StepTags
import com.wini.owle.entity.TypeUnionMembers
import com.wini.owle.entity.typing.TypeUnionMemberDao
import com.wini.owle.entity.workflows.StepDao
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a step
 */
class StepTagDao(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<StepTagDao>(StepTags) {

    }

    /**
     * The workflow for which the tag is applied
     */
    var step by StepDao referencedOn StepTags.step

    /**
     * The tag associated with the step
     */
    var tag by TagDao referencedOn StepTags.tag
}