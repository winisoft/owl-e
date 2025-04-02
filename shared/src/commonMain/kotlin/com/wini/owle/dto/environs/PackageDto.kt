package com.wini.owle.dto.environs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class PackageDto(
    val id: Long,
    val name: String,
    val disambiguation: String?,
    val versionSpec: VersionSpecDto
) : BaseDto