package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class VersionSpecDto(
    override val id: Long,
    val versionMin: String? = null,
    val versionMax: String? = null,
    val versionExact: String? = null
) : StandardDto