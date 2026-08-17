package com.wini.owle.dto.typing

import com.wini.owle.dto.ULongDto
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.error.PositiveValueULongError
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 * Allows capturing constraints on type variables in complex type declarations.
 */
@ModelDefinition
@ApiEndpointDefinition("api.typing.typeVarBound", ["type"], "/type-bound")
@Serializable
data class TypeVarBound(

    /**
     * Relationship to the bound type declaration.
     */
    override val id: ULong,

    val boundTypeNode: TypeNode,

    /**
     * Relationship to the type variable declaration.
     */
    val typeVarNode: TypeNode

) : ULongDto

