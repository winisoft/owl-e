package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.WorkflowFolderTags
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass


/**
 * Represents a tag a given user has associated with a workflow folder
 */
class WorkflowFolderTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<WorkflowFolderTagEntity>(WorkflowFolderTags)

    /**
     * The workflow folder for which the tag is applied
     */
    var workflowFolder by WorkflowFolderEntity referencedOn WorkflowFolderTags.workflowFolder

    /**
     * The tag associated with the workflow folder
     */
    var tag by TagEntity referencedOn WorkflowFolderTags.tag
}
