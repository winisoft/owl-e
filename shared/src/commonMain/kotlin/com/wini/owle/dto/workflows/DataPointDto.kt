package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class DataPointDto(
    val id: Long,
    val workflow: WorkflowDto,
    val name: String,
    val description: String? = null,
    val presetValue: String? = null,
    val storeMethod: StoreMethodDto,
    val typeDeclaration: TypeDeclarationDto,
    val createdAt: String,
    val updatedAt: String
) : BaseDto