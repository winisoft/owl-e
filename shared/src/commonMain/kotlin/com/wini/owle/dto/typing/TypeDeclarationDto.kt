package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeDeclarationDto(
    override val id: Long,
    val typeDefinition: TypeDefinitionDto,
    val alias: String? = null
) : StandardDto