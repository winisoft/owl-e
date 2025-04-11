package com.wini.owle.dto.transforms

import kotlinx.serialization.Serializable

@Serializable
sealed class TransformOccurrenceDto(val id: String) {

    @Serializable
    data object Provision : TransformOccurrenceDto("PROVISION")

    @Serializable
    data object Revision : TransformOccurrenceDto("REVISION")

    @Serializable
    data object Inquisition : TransformOccurrenceDto("INQUISITION")

    @Serializable
    data object PreReqInjection : TransformOccurrenceDto("PREREQ_INJECTION")
}