package com.wini.owle.air.api.operations

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ExternalDocumentation(
    private val description: String? = null,
    private val url: String? = null,
    private val extensions: Map<String, @Contextual Any>? = null
)