package com.wini.owle.dto.transforms

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents a transformation process with a specified return type and related arguments.
 * A transformation can have multiple arguments, target types, and applied transformations.
 */
@OwleModel
@Serializable
data class Transform(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Name of the transformation.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,
    /**
     * Detailed description of what the transformation does.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String,

    /**
     * Optional examples to demonstrate the transformation's usage.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_EX_LENGTH)
    val examples: String?,

    /**
     * The return type of the transformation, if defined.
     */
    val returnsType: TypeDeclaration?

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
        const val MAX_EX_LENGTH = 512
    }

}