package com.wini.owle.dto.runs

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable


@Serializable
data class BenchmarkingDataDto(
    override val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val totalExecutionTime: Double,
    val totalSteps: Int,
    val aiDeterminedSteps: Int,
    val predeterminedSteps: Int
) : StandardDto