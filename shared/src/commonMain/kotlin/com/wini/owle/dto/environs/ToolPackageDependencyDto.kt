package com.wini.owle.dto.environs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable


@Serializable
data class ToolPackageDependencyDto(
    val toolId: Long,
    val packageId: Long,
    val versionSpecId: Long
) : BaseDto