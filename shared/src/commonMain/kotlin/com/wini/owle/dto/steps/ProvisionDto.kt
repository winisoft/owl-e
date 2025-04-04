package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.ToolParamDto
import kotlinx.serialization.Serializable

@Serializable
data class ProvisionDto(
    val id: Long,
    val step: StepDto,
    val toolParam: ToolParamDto,
    val dataPoints: List<DataPointDto>
) : BaseDto