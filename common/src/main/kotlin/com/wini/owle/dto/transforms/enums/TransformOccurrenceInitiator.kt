package com.wini.owle.dto.transforms.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class TransformOccurrenceInitiator(override val dbValue: String) : DbToken {

    DATA_POINT_READ("DATA_POINT_READ"),

    DATA_POINT_WRITE("DATA_POINT_WRITE"),

    INQUISITION("INQUISITION"),

    DATA_POINT_INJECTION("DATA_POINT_INJECTION")

}