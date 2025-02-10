package com.wini.owle.models.metadata

import kotlinx.serialization.Serializable

@Serializable
enum class ModificationTerms {
    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
}