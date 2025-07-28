package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.StringNotEmpty
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
    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    /**
     * Detailed description of what the argument represents.
     */
    @StringLengthMin(1)
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