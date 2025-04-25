package com.wini.owle.dto.transforms.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TransformOccurrence {

    PROVISION,

    REVISION,

    INQUISITION,

    PREREQ_INJECTION

}