package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

/**
 * Represents a unique Python library package, along with its specific version details.
 * Used to store information about packages that can be installed in the workflow.
 */
@Serializable
data class PyPackage(
    override val id: Long,
    /**
     * The name of the Python library package (e.g., 'numpy', 'pandas').
     * Required.
     */
    val name: String,
    /**
     * Optional field to distinguish between packages with similar names.
     * Useful if the package is a fork or variant.
     */
    val disambiguation: String?,
    /**
     * Defines the exact version details (min, max, or exact version) available for this package.
     */
    val versionSpec: VersionSpec
) : StandardDto