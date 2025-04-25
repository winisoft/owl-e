package com.wini.owle.dto.transforms

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
@Serializable
data class Transform(
    override val id: Long,
    /**
     * Name of the transformation.
     */
    val name: String,
    /**
     * Detailed description of what the transformation does.
     */
    val description: String,
    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    val examples: String?,
    /**
     * The return type of the transformation, if defined.
     */
    val returnsType: TypeDeclaration?
) : StandardDto