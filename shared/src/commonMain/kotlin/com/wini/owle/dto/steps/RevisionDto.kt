package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.Serializable

@Serializable
data class RevisionDto(
    val id: Long,
    val dataPoint: DataPointDto,
    val step: StepDto,
) : BaseDto