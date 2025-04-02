package com.wini.owle.dto.environs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class PackageRepoDto(
    val id: Long,
    val sourceType: PackageRepoTypeDto,
    val sourceUrl: String?
) : BaseDto