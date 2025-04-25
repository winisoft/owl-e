package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents bounds on type variables, supporting constraints like 'T: int' for generic types.
 * Allows capturing constraints on type variables in complex type declarations.
 */
@Serializable
data class TypeBound(
    override val id: Long,
    /**
     * Relationship to the bound type declaration.
     */
    val boundTypeDeclaration: TypeDeclaration,
    /**
     * Relationship to the type variable declaration.
     */
    val typeVariableDeclaration: TypeDeclaration
) : StandardDto