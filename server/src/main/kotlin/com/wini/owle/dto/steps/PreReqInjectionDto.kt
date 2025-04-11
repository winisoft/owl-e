package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPointDto
import kotlinx.serialization.Serializable

@Serializable
data class PreReqInjectionDto(
    override val id: Long,
    val junction: WorkflowJunctionDto,
    val sourceDataPoint: DataPointDto,
    val injectedDataPoint: DataPointDto,
    val name: String?,
    val description: String?
) : StandardDto