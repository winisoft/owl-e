package com.wini.owle.dto.user.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class ModificationDto {
    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
}