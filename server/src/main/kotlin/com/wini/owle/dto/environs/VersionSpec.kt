package com.wini.owle.dto.environs

import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
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
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionMin: String? = null,

    /**
     * Maximum version of the package, inclusive.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionMax: String? = null,

    /**
     * Exact version of the package, if specified. Overrides min and max if present.
     */
    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_VERSION_SEGMENT_LENGTH)
    val versionExact: String? = null

) : StandardDto {

    companion object {
        const val MAX_VERSION_SEGMENT_LENGTH = 127
    }
}