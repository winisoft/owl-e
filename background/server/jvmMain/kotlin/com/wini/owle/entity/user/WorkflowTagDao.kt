package com.wini.owle.entity.user

import com.wini.owle.entity.TagRelations
import com.wini.owle.entity.WorkflowTags
import com.wini.owle.entity.workflows.WorkflowDao
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a tag a given user has associated with a workflow
 */
class WorkflowTagDao(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkflowTagDao>(WorkflowTags) {

        /**
         * Creates a new WorkflowTagDao by building the CompositeID from the
         * composite objects. This matches typical Exposed usage for composite keys.
         */
        fun create(workflow: WorkflowDao, tag: TagDao): WorkflowTagDao {
            val compositeId = CompositeID {
                it[WorkflowTags.workflow] = workflow.id
                it[WorkflowTags.tag] = tag.id
            }
            return WorkflowTagDao(EntityID(compositeId, WorkflowTags)).apply {
                this.workflow = workflow
                this.tag = tag
            }
        }
    }

    /**
     * The workflow for which the tag is applied
     */
    var workflow by WorkflowDao referencedOn WorkflowTags.workflow

    /**
     * The tag associated with the workflow
     */
    var tag by TagDao referencedOn WorkflowTags.tag
}