package com.wini.owle.dto.workflows

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class PhaseDto(
    override val id: Long = 0L,
    val workflow: WorkflowDto,
    val parentPhase: PhaseDto?,
    val name: String,
    val description: String?,
    val displayOrder: Int
) : StandardDto