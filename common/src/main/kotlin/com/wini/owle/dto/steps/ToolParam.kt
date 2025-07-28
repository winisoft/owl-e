package com.wini.owle.dto.steps

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.StringNotEmpty
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