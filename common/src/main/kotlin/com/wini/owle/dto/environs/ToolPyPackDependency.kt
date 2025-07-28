package com.wini.owle.dto.environs

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.OwleModel
import com.wini.owle.dto.CompositeDto
import com.wini.owle.error.PositiveValueLongError
import kotlinx.serialization.Serializable

/**
 * Links a tool to the packages it depends on, with specific version constraints.
 * Each dependency is associated with a version specification to ensure compatibility.
 *
 * @param toolId The id of the tool associated with this package dependency.
 * @param packageId The id of the PyPackage this dependency points to.
 * @param versionSpecId The id of the version specification associated with this package dependency.
 */
@OwleModel
@Serializable
data class ToolPyPackDependency(

    @LongValueMin(0)
    val toolId: Long,

    @LongValueMin(0)
    val packageId: Long,

    @LongValueMin(0)
    val versionSpecId: Long

) : CompositeDto {

    override fun idToString(): String {
        return "toolId=$toolId,packageId=$packageId,versionSpecId=$versionSpecId"
    }

}