package com.wini.owle.dto.typing

import com.wini.owle.dto.CompositeDto
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable

/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 * Allows capturing constraints on type variables in complex type declarations.
 */
@ModelDefinition
@Serializable
data class TypeVarBound(

    /**
     * Relationship to the bound type declaration.
     */
    val boundTypeNode: TypeNode,

    /**
     * Relationship to the type variable declaration.
     */
    val typeVarNode: TypeNode

) : CompositeDto {

    override val owleId: String get() = ""

}

