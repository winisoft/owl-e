package com.wini.owle.dto.runs.test

import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import kotlinx.serialization.Serializable

@Serializable
data class TestCase(
    override val id: Long,
    val user: User,
    val name: String,
    val description: String?,
    val workflow: Workflow?,
    val step: Step?,
    val inputs: List<TestInput>,
    val expectedOutcomes: List<TestExpectation>,
    val results: List<WorkflowTestResult>
) : StandardDto