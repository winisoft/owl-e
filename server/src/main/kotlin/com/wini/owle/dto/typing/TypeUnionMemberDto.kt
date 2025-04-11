package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeUnionMemberDto(
    val unionTypeDeclarationId: Long,
    val memberTypeDeclarationId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "unionTypeDeclarationId={$unionTypeDeclarationId}&memberTypeDeclarationId={$memberTypeDeclarationId}"
    }
}