package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeParameterDto(
    override val id: Long,
    val typeDeclaration: TypeDeclarationDto,
    val position: Int,
    val parameterTypeDeclaration: TypeDeclarationDto
) : StandardDto