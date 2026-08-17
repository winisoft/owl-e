package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
@ModelDefinition
@ApiEndpointDefinition("api.typing.typeSchemaRef", ["type"], "/type-schema-ref")
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
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.TypeSchemaRef.NAME_MAX_LENGTH)
    val name: String,

    /**
     * Type declaration associated with this schema reference.
     */
    val typeNode: TypeNode,

    /**
     * Optional description providing details on the schema reference's purpose.
     */

    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.TypeSchemaRef.DESC_MAX_LENGTH)
    val description: String?,
    val children: List<TypeSchemaRef>
) : ULongDto