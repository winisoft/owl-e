package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable


@Serializable
data class StepBenchmarkingDataDto(
    val id: Long,
    val stepExecution: StepExecutionDto,
    val executionTime: Double
) : BaseDto