package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeVarianceDto(
    val id: Long,
    val typeVariableDeclaration: TypeDeclarationDto,
    val varianceCardinality: VarianceCardinalityDto
) : BaseDto