package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.StepDto
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StepExecutionDto(
    val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val step: StepDto,
    val startedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    val status: ExecutionStatusDto
) : BaseDto