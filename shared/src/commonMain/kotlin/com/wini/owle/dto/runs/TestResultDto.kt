package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class TestResultDto(
    val id: Long,
    val testCase: TestCaseDto,
    val workflowExecution: WorkflowExecutionDto,
    val passed: Boolean,
    val executedAt: String
) : BaseDto