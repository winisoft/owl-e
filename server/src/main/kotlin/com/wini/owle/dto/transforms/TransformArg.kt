package com.wini.owle.dto.transforms

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import kotlinx.serialization.Serializable

/**
 * Represents an argument for a transformation with its type and description.
 */
@OwleModel
@Serializable
data class TransformArg(

    @LongValueMin(0)
    override val id: Long,
    /**
     * The transformation this argument belongs to.
     */
    val transformation: Transform,
    /**
     * The type declaration for this argument.
     */
    val typeDeclaration: TypeDeclaration,
    /**
     * Name of the argument.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Detailed description of what the argument represents.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String?
) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 1024
    }
}