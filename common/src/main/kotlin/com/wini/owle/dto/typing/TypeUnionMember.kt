package com.wini.owle.dto.typing

import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
@OwleModel
@Serializable
data class TypeUnionMember(
    /**
     * Relationship to the union type declaration this member belongs to.
     */
    @LongValueMin(0)
    val unionTypeDeclarationId: Long,

    /**
     * Relationship to the member type declaration within the union.
     */
    @LongValueMin(0)
    val memberTypeDeclarationId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "unionTypeDeclarationId=$unionTypeDeclarationId,memberTypeDeclarationId=$memberTypeDeclarationId"
    }
}