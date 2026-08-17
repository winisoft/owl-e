package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.CompositeDto
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringMaxLengthError
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.Schema
import com.wini.owle.schema.error.ConstraintSimple
import com.wini.owle.schema.error.ConstraintStringLenMax
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition


@ModelDefinition
@ApiEndpointDefinition("api.environs.pkg.packageExtra", ["environs"], "/package-extra")
@Serializable
data class PackageExtra(
    val packageVersion: com.wini.owle.dto.environs.version.Version,
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.PackageExtra.NAME_MAX_LENGTH)
    val name: String,
    val distribution: Distribution
): CompositeDto {

    override val owleId: String get() = "distribution=${distribution.owleId},packageVersion=${packageVersion.owleId},name=$name"

}