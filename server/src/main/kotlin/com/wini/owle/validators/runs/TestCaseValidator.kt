package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.test.TestCase
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult
import com.wini.owle.validators.steps.StepValidator
import com.wini.owle.validators.user.UserValidator
import com.wini.owle.validators.workflows.WorkflowValidator

class TestCaseValidator(
    private val userValidator: UserValidator,
    private val workflowValidator: WorkflowValidator,
    private val stepValidator: StepValidator
) : BaseValidator<TestCase>() {
    companion object {
        const val MAX_NAME_LENGTH = 255
    }

    override fun validate(dto: TestCase): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested user
        violations.addAll(userValidator.validate(dto.user).violations)

        // name is required and has max length
        if (dto.name.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "name"))
        }
        if (dto.name.length > MAX_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "name", MAX_NAME_LENGTH))
        }

        // Validate optional workflow if present
        dto.workflow?.let { workflow ->
            violations.addAll(workflowValidator.validate(workflow).violations)
        }

        // Validate optional step if present
        dto.step?.let { step ->
            violations.addAll(stepValidator.validate(step).violations)
        }

        // Either workflow or step must be present
        if (dto.workflow == null && dto.step == null) {
            violations.add(TypeValidation.TestCaseSubject(
                dto.typeName,
                dto.idToString()
            ))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}