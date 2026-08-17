package com.wini.owle.dto.environs.marker

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.error.StringContainsRestrictedChars
import com.wini.owle.error.StringNotBlankError
import com.wini.owle.error.StringNotEmptyError
import com.wini.owle.schema.ModelDefinition
import com.wini.owle.schema.error.ConstraintSimple
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.environs.marker.envMarkerComparison", ["environs"], "/env-marker-comparison")
@Serializable
data class EnvMarkerComparison(

    override val id: ULong,

    val expression: EnvMarkerExpression,

    val variable: EnvMarkerVariable?,

    val operator: EnvMarkerOperator,

    // TEXT column in the DB (no CHARACTER_MAXIMUM_LENGTH) — no length cap sourced from the CSV.
    @ConstraintSimple([StringNotEmptyError::class, StringNotBlankError::class, StringContainsRestrictedChars::class])
    val value: String

): ULongDto

