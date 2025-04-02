package com.wini.owle.dto.transforms

import com.wini.owle.dto.BaseDto
import com.wini.owle.dto.workflows.DataPointDto
import com.wini.owle.dto.typing.TypeDeclarationDto
import kotlinx.serialization.Serializable

@Serializable
data class AppliedTransformDto(
    val id: Long,
    val transformation: TransformationDto,
    val occurrence: TransformOccurrenceDto,
    val resultType: TypeDeclarationDto,
    val dataPoint: DataPointDto?
) : BaseDto