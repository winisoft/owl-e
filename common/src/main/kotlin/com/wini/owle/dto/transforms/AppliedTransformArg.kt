package com.wini.owle.dto.transforms

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
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