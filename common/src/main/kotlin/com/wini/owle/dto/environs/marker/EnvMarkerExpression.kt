package com.wini.owle.dto.environs.marker

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable

@ModelDefinition
@Serializable
data class EnvMarkerExpression(

    override val id: ULong,

    val parent: EnvMarkerExpression?,

    val mode: EnvMarkerExpressionMode

): ULongDto