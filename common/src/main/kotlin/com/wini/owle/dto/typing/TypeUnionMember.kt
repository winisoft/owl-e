package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.CompositeDto
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable


/**
 * Represents individual member types within a union type declaration.
 * Supports union types such as 'Union[int, str]' by linking each member type to the union type declaration.
 */
@ModelDefinition
@Serializable
data class TypeUnionMember(

    /**
     * Relationship to the union type declaration this member belongs to.
     */
    val unionTypeNode: TypeNode,

    /**
     * Relationship to the member type declaration within the union.
     */
    val memberTypeNode: TypeNode

) : CompositeDto {

    override val owleId: String get() = "unionTypeDeclarationId=$unionTypeNode,memberTypeDeclarationId=$memberTypeNode"

}