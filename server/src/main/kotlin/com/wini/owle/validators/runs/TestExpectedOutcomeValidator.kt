package com.wini.owle.validators.runs

import com.wini.owle.async.TypeValidation
import com.wini.owle.dto.runs.TestExpectedOutcomeDto
import com.wini.owle.validators.BaseValidator
import com.wini.owle.validators.ValidationResult

class TestExpectedOutcomeValidator : BaseValidator<TestExpectedOutcomeDto>() {
    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
    }

    override fun validate(dto: TestExpectedOutcomeDto): ValidationResult {
        val violations = mutableListOf<TypeValidation>()

        // testCase is required (non-nullable reference)
        if (dto.testCase == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "testCase"))
        }

        // dataPointName is required and has max length
        if (dto.dataPointName.isBlank()) {
            violations.add(TypeValidation.StringBlank(dto.idToString(), "dataPointName"))
        }
        if (dto.dataPointName.length > MAX_DATAPOINT_NAME_LENGTH) {
            violations.add(TypeValidation.MaxLength(dto.idToString(), "dataPointName", MAX_DATAPOINT_NAME_LENGTH))
        }

        // expectedValue is optional (nullable text)

        // comparisonOperator is required (non-nullable enum)
        if (dto.comparisonOperator == null) {
            violations.add(TypeValidation.RequiredField(dto.idToString(), "comparisonOperator"))
        }

        return if (violations.isEmpty()) ValidationResult.valid() else ValidationResult.invalid(violations)
    }
}