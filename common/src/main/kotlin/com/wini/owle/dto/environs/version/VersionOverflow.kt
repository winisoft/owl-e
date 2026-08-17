package com.wini.owle.dto.environs.version

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.version.enums.OverflowViolation
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
@ApiEndpointDefinition("api.environs.version.versionOverflow", ["environs"], "/version-overflow")
@Serializable
class VersionOverflow(
    override val id: ULong,
    val version: Version,
    val violation: OverflowViolation,
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    @ConstraintStringLenMax(StringMaxLengthError::class, Schema.VersionOverflow.STRING_VALUE_MAX_LENGTH)
    val stringValue: String
): ULongDto