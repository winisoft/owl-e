package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeVarianceDto(
    override val id: Long,
    val typeVariableDeclaration: TypeDeclarationDto,
    val varianceCardinality: VarianceCardinalityDto
) : StandardDto