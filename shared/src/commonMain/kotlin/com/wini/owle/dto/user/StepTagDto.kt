package com.wini.owle.dto.user

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class StepTagDto(
    val stepId: Long,
    val tagId: Long
) : BaseDto