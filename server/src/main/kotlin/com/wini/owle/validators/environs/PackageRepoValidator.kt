package com.wini.owle.validators.environs

import com.wini.owle.error.FieldValidation
import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = PyPackageRepo::class)
class PackageRepoValidator: BaseValidator<PyPackageRepo>() {

    companion object {
        const val MAX_SOURCE_URL_LEN = 1024
    }

    override fun fieldValidations(dto: PyPackageRepo): List<FieldValidation> {
        return emptyList()
    }

}
