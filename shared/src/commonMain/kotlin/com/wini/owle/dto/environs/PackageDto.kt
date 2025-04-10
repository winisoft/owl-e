package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class PackageDto(
    override val id: Long,
    val name: String,
    val disambiguation: String?,
    val versionSpec: VersionSpecDto
) : StandardDto