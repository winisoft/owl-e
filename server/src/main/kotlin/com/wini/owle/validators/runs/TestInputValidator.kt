package com.wini.owle.validators.runs

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.dto.runs.test.TestInput
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = TestInput::class)
class TestInputValidator(
    private val testCaseValidator: TestCaseValidator
) : BaseValidator<TestInput>() {

    companion object {
        const val MAX_DATAPOINT_NAME_LENGTH = 255
    }

    override fun fieldValidations(dto: TestInput):List<FieldValidation> {
        return listOf()
    }}