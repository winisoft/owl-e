package com.wini.owle.dto.typing

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
@OwleModel
@Serializable
data class TypeSchemaRef(

    @LongValueMin(0)
    override val id: Long,
    /**
     * Reference to the parent schema, allowing nested or recursive schemas.
     */
    val parentRef: TypeSchemaRef?,

    /**
     * The name of the property or field within the schema.
     */
    @StringMustNotContainRestricted
    @StringLengthMin(0)
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Type declaration associated with this schema reference.
     */
    val typeDeclaration: TypeDeclaration,

    /**
     * Optional description providing details on the schema reference's purpose.
     */

    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String?,
    val childRefs: List<TypeSchemaRef>
) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 5012
    }
}