package com.wini.owle.dto.transforms

import com.wini.owle.dto.BaseDto
import kotlinx.serialization.Serializable

@Serializable
data class AppliedTransformArgDto(
    val id: Long,
    val appliedTransform: AppliedTransformDto,
    val transformationArg: TransformationArgDto,
    val value: String
) : BaseDto