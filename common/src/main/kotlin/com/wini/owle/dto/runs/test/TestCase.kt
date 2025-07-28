package com.wini.owle.dto.runs.test

import com.wini.owle.schema.error.PositiveValueLong
import com.wini.owle.schema.error.StringSafeChars
import com.wini.owle.schema.OwleModel
import com.wini.owle.schema.error.StringLengthMax
import com.wini.owle.schema.error.StringNotBlank
import com.wini.owle.dto.StandardDto
import com.wini.owle.dto.runs.workflow.WorkflowTestResult
import com.wini.owle.dto.workflows.Step
import com.wini.owle.dto.user.User
import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.error.ListMustNotBeEmpty
import com.wini.owle.error.PositiveValueLongError
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.error.StringSafeCharsError
import com.wini.owle.schema.error.ListHasMembers
import com.wini.owle.schema.error.StringNotEmpty
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