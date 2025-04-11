package com.wini.owle.dto.transforms

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@Serializable
data class TransformationTargetTypeDto(
    val transformationId: Long,
    val typeDeclarationId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "transformationId={$transformationId}&typeDeclarationId={$typeDeclarationId}"
    }

}