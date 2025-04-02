package com.wini.owle.dto.typing

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.environs.VersionSpecDto
import kotlinx.serialization.Serializable

@Serializable
data class TypeDefinitionDto(
    val id: Long,
    val name: String,
    val namespace: String,
    val versionSpec: VersionSpecDto
) : BaseDto