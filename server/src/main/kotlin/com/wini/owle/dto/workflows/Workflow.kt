package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a workflow, containing steps and data points, and supports organization
 * into folders. Metadata is stored as JSON to allow for flexible information capture.
 */
@Serializable
data class Workflow(
    override val id: Long,
    
    /**
     * Name of the workflow, used for identification in the UI.
     */
    val name: String,
    
    /**
     * Description of the workflow's purpose or function.
     */
    val description: String?,
    
    /**
     * Specifies the Python version required to execute the workflow.
     * Defaults to the system's default if not provided.
     */
    val pythonVersion: String,
    
    /**
     * References the folder in which this workflow is stored.
     */
    val parentFolder: WorkflowFolder?,
    
    /**
     * Timestamp for when the workflow was created.
     */
    val createdAt: String,
    
    /**
     * Timestamp for when the workflow was last updated.
     */
    val updatedAt: String
) : StandardDto