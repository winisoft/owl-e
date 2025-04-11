package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeBoundsDto(
    override val id: Long,
    val boundTypeDeclaration: TypeDeclarationDto,
    val typeVariableDeclaration: TypeDeclarationDto
) : StandardDto