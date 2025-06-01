package com.wini.owle.entity.tags.targets

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.WorkflowFolderTags
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import org.jetbrains.exposed.v1.dao.CompositeEntity
import org.jetbrains.exposed.v1.dao.CompositeEntityClass
import org.jetbrains.exposed.v1.core.dao.id.CompositeID
import org.jetbrains.exposed.v1.core.dao.id.EntityID


/**
 * Represents a tag a given user has associated with a workflowFolder
 */
class WorkflowFolderTagEntity(id: EntityID<CompositeID>) : CompositeEntity(id) {

    companion object : CompositeEntityClass<WorkflowFolderTagEntity>(WorkflowFolderTags)

    /**
     * The workflowFolder for which the tag is applied
     */
    var workflowFolder by WorkflowFolderEntity referencedOn WorkflowFolderTags.workflowFolder

    /**
     * The tag associated with the workflowFolder
     */
    var tagEntity by TagEntity.Companion referencedOn WorkflowFolderTags.tag

}