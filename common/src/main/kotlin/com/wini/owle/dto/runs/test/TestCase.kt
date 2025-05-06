package com.wini.owle.dto.runs.test

import com.wini.owle.content.decorator.ListHasMembers
import com.wini.owle.schema.StringMustNotContainRestricted
import com.wini.owle.schema.LongValueMin
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.StringLengthMax
import com.wini.owle.schema.StringLengthMin
import com.wini.owle.schema.StringMustNotBeBlank
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

    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_NAME_LENGTH)
    val name: String,

    @StringLengthMin(1)
    @StringMustNotContainRestricted
    @StringMustNotBeBlank
    @StringLengthMax(MAX_DESC_LENGTH)
    val description: String?,

    val workflow: Workflow?,

    val step: Step?,

    val inputs: List<TestInput>,

    @ListHasMembers
    val expectedOutcomes: List<TestExpectation>,

    val results: List<WorkflowTestResult>

) : StandardDto {

    companion object {
        const val MAX_NAME_LENGTH = 255
        const val MAX_DESC_LENGTH = 16_000
    }
}