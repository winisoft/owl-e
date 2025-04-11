package com.wini.owle.dto.environs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class PackageRepoDto(
    override val id: Long,
    val sourceType: PackageRepoTypeDto,
    val sourceUrl: String?
) : StandardDto