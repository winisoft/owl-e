package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeUnionMemberDto(
    val unionTypeDeclarationId: Long,
    val memberTypeDeclarationId: Long
) : BaseDto {

    @Serializable
    data class CompositeIdDto(
        val unionTypeDeclarationId: Long,
        val memberTypeDeclarationId: Long
    )

}