package com.wini.owle.dto.workflows

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a tool that can be utilized within a step.
 * Contains the tool's body, optional description, return type, and dependencies.
 */
@ModelDefinition
@Serializable
data class Tool(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Name of the tool.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_512)
    val name: String,

    /**
     * Optional description of the tool.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String? = null,

    /**
     * The main body or code of the tool.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_64000)
    val body: String,

    /**
     * The return type of the tool, if defined.
     */
    val returnTypeDeclaration: TypeNode?

) : ULongDto