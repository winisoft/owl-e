package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents specific usages or instances of types, including aliases and complex structures.
 * Each type declaration can represent either a basic type usage or a complex structure using type parameters.
 */
@Serializable
data class TypeDeclaration(
    override val id: Long,
    /**
     * Relationship to the type definition this declaration is based on.
     */
    val typeDefinition: TypeDefinition,
    /**
     * Optional alias for the type declaration, e.g., 'AliasForType'.
     */
    val alias: String? = null
) : StandardDto