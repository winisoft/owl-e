package com.wini.owle.validators.runs

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.steps.StepValidator
import com.wini.owle.validators.user.UserValidator
import com.wini.owle.validators.workflows.WorkflowValidator


@OwleValidator(model = TestCase::class)
class TestCaseValidator(
    private val userValidator: UserValidator,
    private val workflowValidator: WorkflowValidator,
    private val stepValidator: StepValidator,
    private val testInputValidator: TestInputValidator,
    private val testExpectationValidator: TestExpectedOutcomeValidator,
    private val resultsValidator: TestResultValidator
) : BaseValidator<TestCase>() {
    override fun fieldValidations(dto: TestCase):List<FieldValidation> {

        return listOf()
    }
}