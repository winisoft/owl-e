package com.wini.owle.validators.runs

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.test.TestExpectation
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TestExpectation::class)
class TestExpectedOutcomeValidator(
    private val testCaseValidator: TestCaseValidator
) : BaseValidator<TestExpectation>() {



    override fun fieldValidations(dto: TestExpectation):List<FieldValidation> {

        return listOf()
    }}