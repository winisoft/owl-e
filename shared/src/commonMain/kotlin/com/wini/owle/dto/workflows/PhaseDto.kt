package com.wini.owle.dto.workflows

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class PhaseDto(
    val id: Long = 0L,
    val workflow: WorkflowDto,
    val parentPhase: PhaseDto?,
    val name: String,
    val description: String?,
    val displayOrder: Int
) : BaseDto