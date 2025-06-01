package com.wini.owle.entity.tags

import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag that can be associated with workflow steps.
 * Tags help categorize or filter steps and have a many-to-many relationship with StepDao.
 */
class TagEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<TagEntity>(Tags)

    /**
     * Unique name of the tag.
     */
    var name by Tags.name

    /**
     * Optional description of the tag.
     */
    var description by Tags.description

    var source by Tags.tagSource

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