package com.wini.owle.dto.transforms

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class TransformationArgDto(
    val id: Long,
    val transformation: TransformationDto,
    val typeDeclaration: TypeDeclarationDto,
    val name: String,
    val description: String?
) : BaseDto