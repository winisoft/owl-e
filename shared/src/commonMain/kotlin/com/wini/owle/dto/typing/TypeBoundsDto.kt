package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeBoundsDto(
    val id: Long,
    val boundTypeDeclaration: TypeDeclarationDto,
    val typeVariableDeclaration: TypeDeclarationDto
) : BaseDto