package com.wini.owle.entity.workflows

import com.wini.owle.entity.tags.TagEntity
import com.wini.owle.entity.tags.WorkflowFolderTags
import com.wini.owle.entity.user.UserEntity
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.jdbc.SizedIterable


/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 *
 * Nesting is expressed twice: [parentFolder] is the folder's own single parent, while
 * `workflow_folder_relations` additionally permits a folder to appear under several parents.
 */
class WorkflowFolderEntity(id: EntityID<ULong>) : ULongEntity(id) {

    companion object : ULongEntityClass<WorkflowFolderEntity>(WorkflowFolders)

    /**
     * Owner of the folder.
     */
    var user by UserEntity referencedOn WorkflowFolders.user

    /**
     * Name of the folder, required and unique for organizing workflows.
     */
    var name by WorkflowFolders.name

    /**
     * Optional description of the folder's purpose or content.
     */
    var description by WorkflowFolders.description

    /**
     * Enclosing folder, if any.
     */
    var parentFolder by WorkflowFolderEntity optionalReferencedOn WorkflowFolders.parentFolder

    /**
     * Timestamp for when the folder was created.
     */
    var createdAt by WorkflowFolders.createdAt

    /**
     * Timestamp for when the folder was last updated.
     */
    var updatedAt by WorkflowFolders.updatedAt

    /**
     * Folders nested directly inside this one.
     */
    val childFolders: SizedIterable<WorkflowFolderEntity>
            by WorkflowFolderEntity optionalReferrersOn WorkflowFolders.parentFolder

    /**
     * Workflows filed directly in this folder.
     */
    val workflows: SizedIterable<WorkflowEntity> by WorkflowEntity optionalReferrersOn Workflows.parentFolder

    /**
     * Additional parents, via the relation table.
     */
    val relatedParents: SizedIterable<WorkflowFolderEntity>
            by WorkflowFolderEntity.via(WorkflowFolderRelations.child, WorkflowFolderRelations.parent)

    /**
     * Additional children, via the relation table.
     */
    val relatedChildren: SizedIterable<WorkflowFolderEntity>
            by WorkflowFolderEntity.via(WorkflowFolderRelations.parent, WorkflowFolderRelations.child)

    val tags: SizedIterable<TagEntity>
            by TagEntity.via(WorkflowFolderTags.workflowFolder, WorkflowFolderTags.tag)
}
