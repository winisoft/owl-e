package com.wini.owle.dto.transforms

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class TransformationDto(
    override val id: Long,
    val name: String,
    val description: String,
    val examples: String?,
    val returnsType: TypeDeclarationDto?
) : StandardDto