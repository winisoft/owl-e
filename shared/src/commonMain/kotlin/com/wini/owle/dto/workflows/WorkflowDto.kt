package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowDto(
    override val id: Long,
    val name: String,
    val description: String?,
    val pythonVersion: String,
    val parentFolder: WorkflowFolderDto?,
    val createdAt: String,
    val updatedAt: String
) : StandardDto