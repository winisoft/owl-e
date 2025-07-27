package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueIntError
import com.wini.owle.error.PositiveValueLongError
import kotlinx.serialization.Serializable

/**
 * Represents parameters used within complex types, allowing for parameterized type declarations.
 * Supports types such as 'List[kotlin.Int]' or 'Dict[str, int]', where 'int' or 'str' would be parameters of 'List' or 'Dict'.
 */
@ModelDefinition
@Serializable
data class TypeParameter(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Relationship to the type declaration this parameter is part of.
     */
    val typeDeclaration: TypeDeclaration,

    /**
     * Position of the parameter within the type declaration, useful for ordered parameters (e.g., Dict[keyType, valueType]).
     */
    @ConstraintSimple([PositiveValueIntError::class])
    val position: Int,

    /**
     * Relationship to the type declaration that defines the parameter's type.
     */
    val parameterTypeDeclaration: TypeDeclaration

) : StandardDto