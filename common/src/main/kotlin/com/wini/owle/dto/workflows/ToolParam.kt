package com.wini.owle.dto.workflows

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.dto.workflows.Step.Companion.MAX_NAME_LENGTH
import kotlinx.serialization.Serializable

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
@OwleModel
@Serializable
data class ToolParam(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Name of the parameter.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
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