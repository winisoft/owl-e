package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
@ModelDefinition
@ApiEndpointDefinition("api.transforms.transform", ["transforms"], "/transform")
@Serializable
data class Transform(

    override val id: ULong,

    /**
     * Name of the transformation.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Transform.NAME_MAX_LENGTH)
    val name: String,
    /**
     * Detailed description of what the transformation does.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Transform.DESC_MAX_LENGTH)
    val description: String?,

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.Transform.EXAMPLES_MAX_LENGTH)
    val examples: String?,

) : ULongDto