package com.wini.owle.dto.environs

import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable

/**
 * Links a tool to the packages it depends on, with specific version constraints.
 * Each dependency is associated with a version specification to ensure compatibility.
 */
@OwleModel
@Serializable
data class ToolPyPackDep(
    /**
     * The tool associated with this package dependency.
     */
    val toolId: Long,
    /**
     * The package this dependency points to.
     */
    val packageId: Long,
    /**
     * The version specification associated with this package dependency.
     */
    val versionSpecId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "toolId=$toolId,packageId=$packageId,versionSpecId=$versionSpecId"
    }
}