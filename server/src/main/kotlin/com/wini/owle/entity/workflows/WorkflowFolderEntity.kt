package com.wini.owle.entity.workflows

import com.wini.owle.entity.WorkflowFolders
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
class WorkflowFolderEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowFolderEntity>(WorkflowFolders)

    /**
     * Name of the folder, required and unique for organizing workflows.
     */
    var name by WorkflowFolders.name

    /**
     * Optional description of the folder's purpose or content.
     */
    var description by WorkflowFolders.description

    /**
     * Timestamp for when the folder was created.
     */
    var createdAt by WorkflowFolders.createdAt

    /**
     * Timestamp for when the folder was last updated.
     */
    var updatedAt by WorkflowFolders.updatedAt

    /**
     * Reference to the parent folder, enabling hierarchical folder structure.
     */
    var parentFolder by WorkflowFolderEntity optionalReferencedOn WorkflowFolders.parentFolder

    /**
     * An icon to distinguish the folder for the user
     */
    var img by WorkflowFolders.img
}