package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
@Serializable
data class ToolParam(
    override val id: Long,
    /**
     * Name of the parameter.
     */
    val name: String,
    
    /**
     * The tool associated with this parameter.
     */
    val tool: Tool,
    
    /**
     * The type declaration associated with this parameter.
     */
    val typeDeclaration: TypeDeclaration,
    
    /**
     * Whether this parameter has a default value.
     */
    val hasDefault: Boolean,
) : StandardDto