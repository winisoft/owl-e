package com.wini.owle.dto.steps

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.StepDto
import kotlinx.serialization.Serializable

@Serializable
data class ErrorWatchDto(
    val id: Long,
    val step: StepDto,
    val errorType: String,
    val triggerStep: StepDto
) : BaseDto