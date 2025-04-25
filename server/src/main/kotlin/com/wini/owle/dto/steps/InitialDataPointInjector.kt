package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.DataPoint
import kotlinx.serialization.Serializable

@Serializable
data class InitialDataPointInjector(
    override val id: Long,
    val junction: WorkflowJunction,
    val sourceDataPoint: DataPoint,
    val injectedDataPoint: DataPoint,
    val name: String?,
    val description: String?
) : StandardDto