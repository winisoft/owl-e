package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TestExpectedOutcomeValidator(
    private val testCaseValidator: TestCaseValidator
) : BaseValidator<TestExpectedOutcomeDto>() {
    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
    }

    override fun validate(dto: TestExpectedOutcomeDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // Validate nested testCase
        violations.addAll(testCaseValidator.validate(dto.testCase).violations)

        // dataPointName is required and has max length
        if (dto.dataPointName.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.typeName, dto.idToString(), "dataPointName"))
        }
        if (dto.dataPointName.length > MAX_DATAPOINT_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.typeName, dto.idToString(), "dataPointName", MAX_DATAPOINT_NAME_LENGTH))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}