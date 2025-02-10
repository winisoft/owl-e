package com.wini.owle.air.api.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ExternalDocs(
    val description: String,
    val url: String,
    val vendorExtensions: Map<String, @Contextual Any> = LinkedHashMap()
)