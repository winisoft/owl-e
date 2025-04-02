package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.user.metadata.UserDesignedMetadataDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowDto(
    val id: Long,
    val name: String,
    val description: String?,
    val pythonVersion: String,
    val parentFolder: WorkflowFolderDto?,
    val metadata: UserDesignedMetadataDto,
    val createdAt: String,
    val updatedAt: String
) : BaseDto