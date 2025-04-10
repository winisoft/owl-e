package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class StepDto(
    override val id: Long,
    val name: String,
    val description: String?,
    val parentPhase: PhaseDto?,
    val displayOrder: Int,
    val workflow: WorkflowDto,
    val isWorkflowHead: Boolean,
    val tool: ToolDto,
    val nextStep: StepDto?
) : StandardDto