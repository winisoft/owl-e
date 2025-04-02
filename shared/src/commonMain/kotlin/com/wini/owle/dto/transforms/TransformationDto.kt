package com.wini.owle.dto.transforms

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class TransformationDto(
    val id: Long,
    val name: String,
    val description: String,
    val examples: String?,
    val returnsType: TypeDeclarationDto?
) : BaseDto