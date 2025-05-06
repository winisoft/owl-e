package com.wini.owle.validators.runs

import com.wini.owle.error.FieldConstraintError
import com.wini.owle.schema.OwleValidator
import com.wini.owle.dto.runs.test.BenchmarkReport
import com.wini.owle.validators.BaseValidator

@OwleValidator(model = BenchmarkReport::class)
class BenchmarkingDataValidator(
    private val workflowExecutionValidator: WorkflowExecutionValidator
) : BaseValidator<BenchmarkReport>() {

    override fun fieldValidations(dto: BenchmarkReport):List<FieldConstraintError> {

        return listOf()
    }
}