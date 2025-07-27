package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents an argument value for an applied transformation.
 */
@ModelDefinition
@Serializable
data class AppliedTransformArg(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,
    /**
     * The applied transformation this argument belongs to.
     */
    val appliedTransform: AppliedTransform,
    /**
     * The transformation argument definition.
     */
    val transformationArg: TransformArg,
    /**
     * The actual value provided for this argument.
     */
    @ConstraintSimple([StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VALUE_LENGTH)
    val value: String
) : StandardDto {

    companion object {
        const val MAX_VALUE_LENGTH = 1024
    }

}