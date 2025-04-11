package com.wini.owle.dto.environs

import com.wini.owle.dto.CompositeDto
import kotlinx.serialization.Serializable


@Serializable
data class ToolPackageDependencyDto(
    val toolId: Long,
    val packageId: Long,
    val versionSpecId: Long
) : CompositeDto {

    override fun idToString(): String {
        return "toolId={$toolId}&packageId={$packageId}&versionSpecId={$versionSpecId}"
    }
}