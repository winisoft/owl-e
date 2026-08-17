package com.wini.owle.dto.environs.marker

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.EnvMarkerExpressionMode
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.environs.marker.envMarkerExpression", ["environs"], "/env-marker-expression")
@Serializable
data class EnvMarkerExpression(

    override val id: ULong,

    val parent: EnvMarkerExpression?,

    val mode: EnvMarkerExpressionMode

): ULongDto