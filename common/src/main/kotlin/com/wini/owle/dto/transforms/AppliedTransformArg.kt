package com.wini.owle.dto.transforms

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
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