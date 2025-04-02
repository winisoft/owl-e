package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.DecimalValue
import kotlinx.serialization.Serializable

@Serializable
data class BenchmarkingDataDto(
    val id: Long,
    val workflowExecution: WorkflowExecutionDto,
    val totalExecutionTime: DecimalValue,
    val totalSteps: Int,
    val aiDeterminedSteps: Int,
    val predeterminedSteps: Int
) : BaseDto