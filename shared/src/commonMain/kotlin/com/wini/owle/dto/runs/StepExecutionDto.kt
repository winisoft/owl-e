package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.StepDto
import kotlinx.serialization.Serializable

@Serializable
data class StepExecutionDto(
    override val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val step: StepDto,
    val startedAt: String,
    val completedAt: String?,
    val status: ExecutionStatusDto
) : StandardDto