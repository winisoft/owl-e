package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeParameterDto(
    val id: Long,
    val typeDeclaration: TypeDeclarationDto,
    val position: Int,
    val parameterTypeDeclaration: TypeDeclarationDto
) : BaseDto