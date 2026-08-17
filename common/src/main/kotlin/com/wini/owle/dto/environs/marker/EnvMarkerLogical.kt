package com.wini.owle.dto.environs.marker

import com.wini.owle.dto.ULongDto
import com.wini.owle.dto.environs.enums.EnvMarkerLogicOp
import com.wini.owle.schema.ModelDefinition
import kotlinx.serialization.Serializable
import com.wini.owle.schema.api.ApiEndpointDefinition

@ModelDefinition
@ApiEndpointDefinition("api.environs.marker.envMarkerLogical", ["environs"], "/env-marker-logical")
@Serializable
data class EnvMarkerLogical(

    override val id: ULong,

    val expression: EnvMarkerExpression,

    val operator: EnvMarkerLogicOp,

    val left: EnvMarkerExpression,

    val right: EnvMarkerExpression

): ULongDto