package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class ExecutionLogDto(
    override val id: Long,
    val stepExecution: StepExecutionDto,
    val timestamp: String,
    val logLevel: LogLevelDto,
    val message: String
) : StandardDto