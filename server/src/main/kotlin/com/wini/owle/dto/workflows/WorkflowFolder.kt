package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a folder for organizing workflows in a hierarchical structure.
 * Supports nested folders and categorizes workflows.
 */
@Serializable
data class WorkflowFolder(
    override val id: Long,
    
    /**
     * Reference to the parent folder, enabling hierarchical folder structure.
     */
    val parentFolder: WorkflowFolder? = null,
    
    /**
     * Name of the folder, required and unique for organizing workflows.
     */
    val name: String,
    
    /**
     * Optional description of the folder's purpose or content.
     */
    val description: String? = null,
    
    /**
     * An icon to distinguish the folder for the user
     */
    val img: ByteArray? = null,
    
    /**
     * Timestamp for when the folder was created.
     */
    val createdAt: String,
    
    /**
     * Timestamp for when the folder was last updated.
     */
    val updatedAt: String
) : StandardDto