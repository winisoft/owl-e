package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.TestCaseDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.user.UserValidator
import com.wini.owle.validators.workflows.WorkflowValidator

class TestCaseValidator(
    private val userValidator: UserValidator,
    private val workflowValidator: WorkflowValidator,
    private val inputsValidator: TestInputValidator,
    private val expectedOutcomesValidator: TestExpectedOutcomeValidator,
    private val resultsValidator: TestResultValidator
) : BaseValidator<TestCaseDto>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TestCaseDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // user is required (non-nullable reference)
        if (dto.user == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "user"))
        }

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // description is optional (nullable text)
        
        // workflow and step are optional (nullable references)
        // but at least one should be present
        if (dto.workflow == null && dto.step == null) {
            violations.add(TypeValidation.Generic(
                dto.idToString(),
                "target",
                "Either workflow or step must be specified"
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}