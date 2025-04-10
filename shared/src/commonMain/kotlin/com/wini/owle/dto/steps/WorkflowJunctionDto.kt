package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.workflows.WorkflowDto
import kotlinx.serialization.Serializable

@Serializable
data class WorkflowJunctionDto(
    override val id: Long,
    val sourceStep: StepDto,
    val targetWorkflow: WorkflowDto,
    val triggerInquisition: InquisitionDto?
) : StandardDto