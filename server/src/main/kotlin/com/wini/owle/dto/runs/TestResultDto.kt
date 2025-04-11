package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class TestResultDto(
    override val id: Long,
    val testCase: TestCaseDto,
    val workflowExecution: WorkflowExecutionDto,
    val passed: Boolean,
    val executedAt: String
) : StandardDto