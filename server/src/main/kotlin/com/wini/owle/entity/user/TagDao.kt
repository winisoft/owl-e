package com.wini.owle.entity.user

import com.wini.owle.entity.*
import com.wini.owle.entity.environs.ToolPackageDependencyDao
import com.wini.owle.entity.environs.VersionSpecDao
import com.wini.owle.entity.environs.VersionSpecDao.Companion.referrersOn
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID


/**
 * Represents a tag that can be associated with workflow steps.
 * Tags help categorize or filter steps and have a many-to-many relationship with StepDao.
 */
class TagDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TagDao>(Tags)

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
    var user by UserDao referencedOn Tags.user

    /**
     * Tags this tag sits above in a custom tag hierarchy
     */
    val childTags by TagDao.via(TagRelations.parentTag, TagRelations.childTag)

    /**
     * Tags this tag sits beneath in a custom tag hierarchy
     */
    val parentTags by TagDao.via(TagRelations.childTag, TagRelations.parentTag)

    /**
     * Steps this tag has been applied to
     */
    val stepsAssociated by StepDao.via(StepTags.tag, StepTags.step)

    /**
     * Workflows this tag has been applied to
     */
    val workflowsAssociated by WorkflowDao.via(WorkflowTags.tag, WorkflowTags.workflow)
}