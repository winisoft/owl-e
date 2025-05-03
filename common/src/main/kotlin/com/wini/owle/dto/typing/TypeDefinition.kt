package com.wini.owle.dto.typing

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.environs.VersionSpec
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