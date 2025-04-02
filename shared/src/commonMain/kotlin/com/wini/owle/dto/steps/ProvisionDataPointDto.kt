package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class ProvisionDataPointDto(
    val provisionId: Long,
    val dataPointId: Long
) : BaseDto