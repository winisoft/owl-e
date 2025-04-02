package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class ExecutionLogDto(
    val id: Long,
    val stepExecution: StepExecutionDto,
    val timestamp: String,
    val logLevel: LogLevelDto,
    val message: String
) : BaseDto