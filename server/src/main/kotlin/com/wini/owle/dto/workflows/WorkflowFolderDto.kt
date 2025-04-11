package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowFolderDto(
    override val id: Long,
    val parentFolder: WorkflowFolderDto? = null,
    val name: String,
    val description: String? = null,
    val img: ByteArray? = null,
    val createdAt: String,
    val updatedAt: String
) : StandardDto