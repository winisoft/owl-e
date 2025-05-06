package com.wini.owle.dto.typing

import com.wini.owle.schema.IntValueMin
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents parameters used within complex types, allowing for parameterized type declarations.
 * Supports types such as 'List[kotlin.Int]' or 'Dict[str, int]', where 'int' or 'str' would be parameters of 'List' or 'Dict'.
 */
@OwleModel
@Serializable
data class TypeParameter(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Relationship to the type declaration this parameter is part of.
     */
    val typeDeclaration: TypeDeclaration,

    /**
     * Position of the parameter within the type declaration, useful for ordered parameters (e.g., Dict[keyType, valueType]).
     */
    @IntValueMin(0)
    val position: Int,

    /**
     * Relationship to the type declaration that defines the parameter's type.
     */
    val parameterTypeDeclaration: TypeDeclaration

) : StandardDto