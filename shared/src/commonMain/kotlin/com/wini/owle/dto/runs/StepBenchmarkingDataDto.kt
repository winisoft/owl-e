package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.DecimalValue
import kotlinx.serialization.Serializable

@Serializable
data class StepBenchmarkingDataDto(
    val id: Long,
    val stepExecution: StepExecutionDto,
    val executionTime: DecimalValue,
    val resourceUsage: ResourceUsageDto?
) : BaseDto