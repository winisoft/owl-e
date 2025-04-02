package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class ToolParamDto(
    val id: Long,
    val name: String,
    val tool: ToolDto,
    val typeDeclaration: TypeDeclarationDto,
    val hasDefault: Boolean,
) : BaseDto