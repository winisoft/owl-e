package com.wini.owle.dto.transforms

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents an argument for a transformation with its type and description.
 */
@Serializable
data class TransformArg(
    override val id: Long,
    /**
     * The transformation this argument belongs to.
     */
    val transformation: Transform,
    /**
     * The type declaration for this argument.
     */
    val typeDeclaration: TypeDeclaration,
    /**
     * Name of the argument.
     */
    val name: String,
    /**
     * Detailed description of what the argument represents.
     */
    val description: String?
) : StandardDto