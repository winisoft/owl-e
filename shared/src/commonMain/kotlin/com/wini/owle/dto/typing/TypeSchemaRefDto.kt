package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeSchemaRefDto(
    override val id: Long,
    val parentRef: TypeSchemaRefDto?,
    val name: String,
    val typeDeclaration: TypeDeclarationDto,
    val description: String?,
    val childRefs: List<TypeSchemaRefDto>
) : StandardDto