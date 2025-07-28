package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents an argument value for an applied transformation.
 */
@OwleModel
@Serializable
data class AppliedTransformArg(

    @LongValueMin(0)
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
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VALUE_LENGTH)
    val value: String
) : StandardDto {

    companion object {
        const val MAX_VALUE_LENGTH = 1024
    }

}