package com.wini.owle.dto.environs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class VersionSpecDto(
    val id: Long,
    val versionMin: String? = null,
    val versionMax: String? = null,
    val versionExact: String? = null
) : BaseDto