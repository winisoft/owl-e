package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowExecutionDto(
    val id: Long,
    val workflow: WorkflowDto,
    val user: UserDto,
    val startedAt: String,
    val completedAt: String?,
    val status: ExecutionStatusDto,
    val stepExecutions: List<StepExecutionDto>
) : BaseDto