package com.wini.owle.entity.workflows

import org.jetbrains.exposed.v1.dao.ULongEntity
import org.jetbrains.exposed.v1.dao.ULongEntityClass
import org.jetbrains.exposed.v1.core.dao.id.EntityID

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
class WorkflowFolderEntity(id: EntityID<ULong>) : ULongEntity(id) {
    companion object : ULongEntityClass<WorkflowFolderEntity>(WorkflowFolders)

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

    var parentFolder: WorkflowFolderEntity? = null

    val parentFolders get() = WorkflowFolderRelationEntity.find { WorkflowFolderRelations.child eq id }.map { it.parent }

    val subclasses get() = WorkflowFolderRelationEntity.find { WorkflowFolderRelations.parent eq id }.map { it.child }

}