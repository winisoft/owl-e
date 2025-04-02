package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeDeclarationDto(
    val id: Long,
    val typeDefinition: TypeDefinitionDto,
    val alias: String? = null
) : BaseDto