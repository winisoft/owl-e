package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class ToolDto(
    val id: Long,
    val name: String,
    val description: String? = null,
    val body: String,
    val returnTypeDeclaration: TypeDeclarationDto?
) : BaseDto