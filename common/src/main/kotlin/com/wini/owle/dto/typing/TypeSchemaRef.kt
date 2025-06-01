package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
@ModelDefinition
@Serializable
data class TypeSchemaRef(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Reference to the parent schema, allowing nested or recursive schemas.
     */
    val parentRef: TypeSchemaRef?,

    /**
     * The name of the property or field within the schema.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Type declaration associated with this schema reference.
     */
    val typeDeclaration: TypeNode,

    /**
     * Optional description providing details on the schema reference's purpose.
     */

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_5012)
    val description: String?,
    val childRefs: List<TypeSchemaRef>
) : ULongDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 5012
    }
}