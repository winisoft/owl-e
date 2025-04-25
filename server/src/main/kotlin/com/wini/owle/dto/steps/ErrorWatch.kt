package com.wini.owle.dto.steps

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.workflows.Step
import kotlinx.serialization.Serializable

@Serializable
data class ErrorWatch(
    override val id: Long,
    val step: Step,
    val errorType: String,
    val triggerStep: Step
) : StandardDto