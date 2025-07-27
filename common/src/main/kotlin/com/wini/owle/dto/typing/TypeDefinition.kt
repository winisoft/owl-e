package com.wini.owle.dto.typing

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Defines the core types in the system, such as 'int', 'str', 'list', 'dict', 'Union', etc.
 * These are foundational definitions that are later instantiated or parameterized in type declarations.
 */
@ModelDefinition
@Serializable
data class TypeDefinition(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Name of the type, e.g., 'int', 'str', 'list'.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Namespace that specifies where this type is defined, e.g., 'builtins' or 'collections'.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAMESPACE_LENGTH)
    val namespace: String,

    /**
     * Relationship to the version specification.
     */
    val versionSpec: VersionSpec

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_NAMESPACE_LENGTH = 512
    }

}