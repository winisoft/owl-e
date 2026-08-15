package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.ULongDto
import com.wini.owle.error.PositiveValueULongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.schema.Schema
import kotlinx.serialization.Serializable

/**
 * Represents an argument value for an applied transformation.
 */
@ModelDefinition
@Serializable
data class TransformOccurrenceArg(

    @ConstraintSimple([PositiveValueULongError::class])
    override val id: ULong,

    /**
     * The applied transformation this argument belongs to.
     */
    val transformOccurrence: TransformOccurrence,

    /**
     * The transformation argument definition.
     */
    val transformationArg: TransformArg,
    /**
     * The actual value provided for this argument.
     */
    @ConstraintSimple([StringContainsRestrictedChars::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.SizeLimits.LEN_1024)
    val value: String
) : ULongDto