package com.wini.owle.entity.user.tags

import com.wini.owle.entity.WorkflowTags
import com.wini.owle.entity.workflows.WorkflowEntity
import org.jetbrains.exposed.dao.CompositeEntity
import org.jetbrains.exposed.dao.CompositeEntityClass
import org.jetbrains.exposed.dao.id.CompositeID
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a tag a given user has associated with a workflow
 */
class WorkflowTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {
    companion object : CompositeEntityClass<WorkflowTagEntity>(WorkflowTags) {

        fun buildId(workflow: WorkflowEntity, tagEntity: TagEntity): CompositeID {
            return CompositeID {
                it[WorkflowTags.workflow] = workflow.id
                it[WorkflowTags.tag] = tagEntity.id
            }
        }

        /**
         * Creates a new WorkflowTagDao by building the CompositeID from the
         * composite objects. This matches typical Exposed usage for composite keys.
         */
        fun create(workflow: WorkflowEntity, tagEntity: TagEntity): WorkflowTagEntity {
            val compositeId = buildId(workflow, tagEntity)
            return WorkflowTagEntity(EntityID(compositeId, WorkflowTags)).apply {
                this.workflow = workflow
                this.tagEntity = tagEntity
            }
        }
    }

    /**
     * The workflow for which the tag is applied
     */
    var workflow by WorkflowEntity referencedOn WorkflowTags.workflow

    /**
     * The tag associated with the workflow
     */
    var tagEntity by TagEntity referencedOn WorkflowTags.tag
}