package com.wini.owle.dto.transforms.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TransformOccurrence {

    DATA_POINT_READ,

    DATA_POINT_WRITE,

    INQUISITION,

    DATA_POINT_INJECTION

}