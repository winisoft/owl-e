package com.wini.owle.validators.environs

import com.wini.owle.error.FieldValidation

import com.wini.owle.ksp.OwleValidator
import com.wini.owle.dto.environs.PyPackage
import com.wini.owle.dto.environs.PyPackageRepo
import com.wini.owle.validators.BaseValidator


@OwleValidator(model = PyPackage::class)
class PackageValidator(
    private val versionSpecValidator: VersionSpecValidator
) : BaseValidator<PyPackage>() {



    override fun fieldValidations(dto: PyPackage):List<FieldValidation> {
        return listOf(
//            FieldValidation.LongValueMustNotBeNegative(PyPackage::id, dto),
//            TextInputValidation(
//                PyPackage::name,
//                dto,
//                MAX_NAME_LENGTH
//            ),
//            TextInputValidation(
//                PyPackage::disambiguation,
//                dto,
//                MAX_DISAMBIGUATION_LENGTH
//            ),
//        ).plus(
//            versionSpecValidator.fieldValidations(dto.versionSpec)
//        )
    )
    }
}