package com.wini.owle.dto.environs.marker

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.EnvMarkerOperator
import com.wini.owle.dto.environs.enums.EnvMarkerVariable
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class EnvMarkerComparison(

    override val id: ULong,

    val expression: EnvMarkerExpression,

    val variable: EnvMarkerVariable?,

    val operator: EnvMarkerOperator,

    val value: String

): ULongDto

