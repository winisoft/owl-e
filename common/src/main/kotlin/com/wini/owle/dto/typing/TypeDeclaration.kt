package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents specific usages or instances of types, including aliases and complex structures.
 * Each type declaration can represent either a basic type usage or a complex structure using type parameters.
 */
@ModelDefinition
@Serializable
data class TypeDeclaration(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Relationship to the type definition this declaration is based on.
     */
    val typeDefinition: TypeDefinition,

    /**
     * Optional alias for the type declaration, e.g., 'AliasForType'.
     */
    @ConstraintSimple([StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_ALIAS_LENGTH)
    val alias: String? = null

) : StandardDto {

    companion object {
        const val MAX_ALIAS_LENGTH = 255
    }

}