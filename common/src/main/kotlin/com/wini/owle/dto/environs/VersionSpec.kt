package com.wini.owle.dto.environs

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.StringNotEmpty
import kotlinx.serialization.Serializable

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
@OwleModel
@Serializable
data class VersionSpec(

    @LongValueMin(0)
    override val id: Long,

    /**
     * Minimum version of the package, inclusive.
     */
    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionMin: String? = null,

    /**
     * Maximum version of the package, inclusive.
     */
    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionMax: String? = null,

    /**
     * Exact version of the package, if specified. Overrides min and max if present.
     */
    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionExact: String? = null

) : StandardDto {

    companion object {
        const val MAX_VERSION_SEGMENT_LENGTH = 127
    }

}