package com.wini.owle.entity.user.tags

import com.wini.owle.entity.*
import com.wini.owle.entity.user.UserEntity
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


/**
 * Represents a tag that can be associated with workflow steps.
 * Tags help categorize or filter steps and have a many-to-many relationship with StepDao.
 */
class TagEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TagEntity>(Tags)

    /**
     * Unique name of the tag.
     */
    var name by Tags.name

    /**
     * Optional description of the tag.
     */
    var description by Tags.description

    /**
     * User this tag belongs to
     */
    var userEntity by UserEntity referencedOn Tags.user

    /**
     * Tags this tag sits above in a custom tag hierarchy
     */
    val childTags by TagEntity.via(TagRelations.parentTag, TagRelations.childTag)

    /**
     * Tags this tag sits beneath in a custom tag hierarchy
     */
    val parentTags by TagEntity.via(TagRelations.childTag, TagRelations.parentTag)

    /**
     * Steps this tag has been applied to
     */
    val stepsAssociated by StepEntity.via(StepTags.tag, StepTags.step)

    /**
     * Workflows this tag has been applied to
     */
    val workflowsAssociated by WorkflowEntity.via(WorkflowTags.tag, WorkflowTags.workflow)
}