package com.wini.owle.dto.environs.pkg

import com.wini.owle.dto.ULongDto
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
    override val id: ULong,
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.PackageExtra.NAME_MAX_LENGTH)
    val name: String,
    val distribution: Distribution
): ULongDto {

    override val owleId: String get() = "id=$id,name=${name},distribution=${distribution.owleId}"

}