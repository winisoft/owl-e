package com.wini.owle.dto.typing

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.VersionSpec
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.StringNotEmpty
import kotlinx.serialization.Serializable

/**
 * Defines the core types in the system, such as 'int', 'str', 'list', 'dict', 'Union', etc.
 * These are foundational definitions that are later instantiated or parameterized in type declarations.
 */
@OwleModel
@Serializable
data class TypeDefinition(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Name of the type, e.g., 'int', 'str', 'list'.
     */
    @StringLengthMin(1)
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    @StringMustNotContainRestricted
    val name: String,

    /**
     * Namespace that specifies where this type is defined, e.g., 'builtins' or 'collections'.
     */
    @StringLengthMin(1)
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAMESPACE_LENGTH)
    @StringMustNotContainRestricted
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