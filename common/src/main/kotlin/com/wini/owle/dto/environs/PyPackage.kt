package com.wini.owle.dto.environs

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable


/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
@ModelDefinition
@Serializable
data class PyPackage(

    /**
     * The unique identifier of this PyPackage
     */
    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * The name of the Python library package (e.g., 'numpy', 'pandas').
     * Required.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_NAME_LENGTH)
    val name: String,

    /**
     * Optional field to distinguish between packages with similar names.
     * Useful if the package is a fork or variant.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringSafeCharsError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_DISAMBIGUATION_LENGTH)
    val disambiguation: String?,

    /**
     * Defines the exact version details (min, max, or exact version) available for this package.
     */
    val versionSpec: VersionSpec

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DISAMBIGUATION_LENGTH = 255
    }
}
