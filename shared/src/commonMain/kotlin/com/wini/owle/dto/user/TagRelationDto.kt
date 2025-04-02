package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TagRelationDto(
    val parentTagId: Long,
    val childTagId: Long
) : BaseDto