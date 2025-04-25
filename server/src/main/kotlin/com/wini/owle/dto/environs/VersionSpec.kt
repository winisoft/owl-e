package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Specifies version constraints for a package, allowing for precise control
 * over the range or exact version of a dependency.
 */
@Serializable
data class VersionSpec(
    override val id: Long,
    /**
     * Minimum version of the package, inclusive.
     */
    val versionMin: String? = null,
    /**
     * Maximum version of the package, inclusive.
     */
    val versionMax: String? = null,
    /**
     * Exact version of the package, if specified. Overrides min and max if present.
     */
    val versionExact: String? = null
) : StandardDto