package com.wini.owle.dto.environs

import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintStringLenMax
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import kotlinx.serialization.Serializable

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
@ModelDefinition
@Serializable
data class VersionSpec(

    @ConstraintSimple([PositiveValueLongError::class])
    override val id: Long,

    /**
     * Minimum version of the package, inclusive.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VERSION_SEGMENT_LENGTH)
    val versionMin: String? = null,

    /**
     * Maximum version of the package, inclusive.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VERSION_SEGMENT_LENGTH)
    val versionMax: String? = null,

    /**
     * Exact version of the package, if specified. Overrides min and max if present.
     */
    @ConstraintSimple([StringNotEmptyError::class, StringSafeCharsError::class, StringNotBlankError::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, MAX_VERSION_SEGMENT_LENGTH)
    val versionExact: String? = null

) : StandardDto {

    companion object {
        const val MAX_VERSION_SEGMENT_LENGTH = 127
    }

}