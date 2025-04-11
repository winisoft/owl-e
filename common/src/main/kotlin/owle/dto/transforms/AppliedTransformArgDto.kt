package com.wini.owle.dto.transforms

import com.wini.owle.dto.StandardDto
import kotlinx.serialization.Serializable

@Serializable
data class AppliedTransformArgDto(
    override val id: Long,
    val appliedTransform: AppliedTransformDto,
    val transformationArg: TransformationArgDto,
    val value: String
) : StandardDto