package com.wini.owle.dto.transforms

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Represents an argument for a transformation with its type and description.
 */
@ModelDefinition
@Serializable
data class TransformArg(

    @ConstraintSimple([PositiveValueLongError::class])
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
    @ConstraintSimple([StringNotEmptyError::class, StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Detailed description of what the argument represents.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_DESC_LENGTH)
    val description: String?
) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 1024
    }
}