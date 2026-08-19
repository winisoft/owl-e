package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.dto.typing.enums.TypeNodeRelationKind
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

/**
 * An edge between two type nodes: inheritance, or generic instantiation.
 */
@ModelDefinition
@Serializable
data class TypeNodeRelation(

    val parentNode: TypeNode,

    val childNode: TypeNode,

    val relationKind: TypeNodeRelationKind,

    /**
     * The base's position in the child's declaration, which fixes the method resolution order:
     * `class C(A, B)` resolves differently from `class C(B, A)`.
     */
    val position: UInt,

) : CompositeDto {

    override val owleId: String get() = "parentNode=${parentNode.owleId},childNode=${childNode.owleId}"
}
