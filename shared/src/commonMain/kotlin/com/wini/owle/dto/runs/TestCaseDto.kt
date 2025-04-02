package com.wini.owle.dto.runs

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.StepDto
import com.wini.owle.dto.user.UserDto
import com.wini.owle.dto.workflows.WorkflowDto
import kotlinx.serialization.Serializable

@Serializable
data class TestCaseDto(
    val id: Long,
    val user: UserDto,
    val name: String,
    val description: String?,
    val workflow: WorkflowDto?,
    val step: StepDto?,
    val inputs: List<TestInputDto>,
    val expectedOutcomes: List<TestExpectedOutcomeDto>,
    val results: List<TestResultDto>
) : BaseDto