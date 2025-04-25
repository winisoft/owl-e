package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a reference within a type schema, supporting nested or hierarchical type structures.
 * Each schema reference is tied to a specific type declaration and may contain child references.
 */
@Serializable
data class TypeSchemaRef(
    override val id: Long,
    /**
     * Reference to the parent schema, allowing nested or recursive schemas.
     */
    val parentRef: TypeSchemaRef?,
    /**
     * The name of the property or field within the schema.
     */
    val name: String,
    /**
     * Type declaration associated with this schema reference.
     */
    val typeDeclaration: TypeDeclaration,
    /**
     * Optional description providing details on the schema reference's purpose.
     */
    val description: String?,
    val childRefs: List<TypeSchemaRef>
) : StandardDto