package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable


@Serializable
data class StepBenchmarkingDataDto(
    override val id: Long,
    val stepExecution: StepExecutionDto,
    val executionTime: Double
) : StandardDto