package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.typing.TypeNode
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents an argument for a transformation with its type and description.
 */
@ModelDefinition
@Serializable
data class TransformArg(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,
    /**
     * The transformation this argument belongs to.
     */
    val transformation: Transform,

    val typeNode: TypeNode,

    /**
     * Name of the argument.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,

    /**
     * Detailed description of what the argument represents.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val description: String?
) : ULongDto