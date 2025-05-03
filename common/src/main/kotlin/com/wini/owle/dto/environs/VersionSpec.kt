package com.wini.owle.dto.environs

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
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