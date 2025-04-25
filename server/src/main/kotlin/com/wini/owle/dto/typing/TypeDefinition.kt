package com.wini.owle.dto.typing

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.VersionSpec
import kotlinx.serialization.Serializable

/**
 * Defines the core types in the system, such as 'int', 'str', 'list', 'dict', 'Union', etc.
 * These are foundational definitions that are later instantiated or parameterized in type declarations.
 */
@Serializable
data class TypeDefinition(
    override val id: Long,
    /**
     * Name of the type, e.g., 'int', 'str', 'list'.
     */
    val name: String,
    /**
     * Namespace that specifies where this type is defined, e.g., 'builtins' or 'collections'.
     */
    val namespace: String,
    /**
     * Relationship to the version specification.
     */
    val versionSpec: VersionSpec
) : StandardDto