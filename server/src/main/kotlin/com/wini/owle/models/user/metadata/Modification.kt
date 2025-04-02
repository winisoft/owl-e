package com.wini.owle.models.user.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class Modification {
    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
}