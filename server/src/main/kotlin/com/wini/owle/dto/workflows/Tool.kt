package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
 */
@Serializable
data class Tool(
    override val id: Long,
    /**
     * Name of the tool.
     */
    val name: String,
    /**
     * Optional description of the tool.
     */
    val description: String? = null,
    /**
     * The main body or code of the tool.
     */
    val body: String,
    /**
     * The return type of the tool, if defined.
     */
    val returnTypeDeclaration: TypeDeclaration?
) : StandardDto