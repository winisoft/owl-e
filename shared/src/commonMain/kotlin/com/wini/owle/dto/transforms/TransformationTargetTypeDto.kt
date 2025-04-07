package com.wini.owle.dto.transforms

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TransformationTargetTypeDto(
    val transformationId: Long,
    val typeDeclarationId: Long
) : BaseDto {

    @Serializable
    data class IdDto(
        val transformationId: Long,
        val typeDeclarationId: Long
    )
}