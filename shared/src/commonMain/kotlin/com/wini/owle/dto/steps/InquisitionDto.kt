package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.Serializable

@Serializable
data class InquisitionDto(
    val id: Long,
    val step: StepDto,
    val className: String,
    val dataPoint: DataPointDto,
    val onMetWorkflowStep: StepDto,
    val executionOrder: Int
) : BaseDto