package com.wini.owle.dto.steps

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Tool
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Represents a parameter for a tool within a workflow.
 * Each parameter has a name, type, and can optionally have a default value.
 */
@ModelDefinition
@Serializable
data class ToolParam(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Name of the parameter.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
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