package com.wini.owle.dto.typing

import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents specific usages or instances of types, including aliases and complex structures.
 * Each type declaration can represent either a basic type usage or a complex structure using type parameters.
 */
@OwleModel
@Serializable
data class TypeDeclaration(
    @LongValueMin(0)
    override val id: Long,

    /**
     * Relationship to the type definition this declaration is based on.
     */
    val typeDefinition: TypeDefinition,

    /**
     * Optional alias for the type declaration, e.g., 'AliasForType'.
     */
    @StringMustNotBeBlank
    @StringLengthMax(MAX_ALIAS_LENGTH)
    val alias: String? = null

) : StandardDto {

    companion object {
        const val MAX_ALIAS_LENGTH = 255
    }

}