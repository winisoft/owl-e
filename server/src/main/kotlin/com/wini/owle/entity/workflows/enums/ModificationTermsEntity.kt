package com.wini.owle.entity.workflows.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ModificationTermsEntity {
    PROHIBITED, ALLOW_MODIFICATION, ALLOW_MODIFICATION_REDISTRIBUTION
}