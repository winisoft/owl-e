package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
@ModelDefinition
@Serializable
data class Transform(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * Name of the transformation.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_255)
    val name: String,
    /**
     * Detailed description of what the transformation does.
     */
    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_16000)
    val description: String?,

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_512)
    val examples: String?,

) : ULongDto