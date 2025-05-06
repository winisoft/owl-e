package com.wini.owle.validators.runs

import com.wini.owle.error.FieldConstraintError

import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TestExpectation::class)
class TestExpectedOutcomeValidator(
    private val testCaseValidator: TestCaseValidator
) : BaseValidator<TestExpectation>() {



    override fun fieldValidations(dto: TestExpectation):List<FieldConstraintError> {

        return listOf()
    }}