package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
@Serializable
data class TypeUnionMember(
    /**
     * Relationship to the union type declaration this member belongs to.
     */
    val unionTypeDeclarationId: Long,
    /**
     * Relationship to the member type declaration within the union.
     */
    val memberTypeDeclarationId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "unionTypeDeclarationId={$unionTypeDeclarationId}&memberTypeDeclarationId={$memberTypeDeclarationId}"
    }
}