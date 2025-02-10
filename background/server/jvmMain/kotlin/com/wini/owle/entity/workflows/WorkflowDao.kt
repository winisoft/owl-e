package com.wini.owle.entity.workflows

import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.Workflows
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
class WorkflowDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<WorkflowDao>(Workflows)

    /**
     * Name of the workflow, used for identification in the UI.
     */
    var name by Workflows.name

    /**
     * Description of the workflow's purpose or function.
     */
    var description by Workflows.description

    /**
     * Specifies the Python version required to execute the workflow.
     * Defaults to the system's default if not provided.
     */
    var pythonVersion by Workflows.pythonVersion

    /**
     * JSON field for storing additional metadata related to the workflow.
     */
    var metadata by Workflows.metadata

    /**
     * Timestamp for when the workflow was created.
     */
    var createdAt by Workflows.createdAt

    /**
     * Timestamp for when the workflow was last updated.
     */
    var updatedAt by Workflows.updatedAt

    /**
     * An icon to distinguish the workflow for the user
     */
    var img by WorkflowFolders.img

    /**
     * References the folder in which this workflow is stored.
     */
    var parentFolder by WorkflowFolderDao optionalReferencedOn Workflows.parentFolder
}