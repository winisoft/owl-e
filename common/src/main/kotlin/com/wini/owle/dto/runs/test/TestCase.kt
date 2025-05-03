package com.wini.owle.dto.runs.test

import com.wini.owle.ksp.StringMustNotContainRestricted
import com.wini.owle.ksp.LongValueMin
import com.wini.owle.ksp.OwleModel
import com.wini.owle.ksp.StringLengthMax
import com.wini.owle.ksp.StringLengthMin
import com.wini.owle.ksp.StringMustNotBeBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import kotlinx.serialization.Serializable

@OwleModel
@Serializable
data class TestCase(
    @LongValueMin(0)
    override val id: Long,

    val user: User,

    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    @StringLengthMin(0)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String?,

    val workflow: Workflow?,

    val step: Step?,

    val inputs: List<TestInput>,

    val expectedOutcomes: List<TestExpectation>,

    val results: List<WorkflowTestResult>

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
    }
}