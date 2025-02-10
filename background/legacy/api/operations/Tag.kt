package com.wini.owle.air.api.operations

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    private val name: String? = null,
    private val description: String? = null,
    private val externalDocs: ExternalDocumentation? = null,
    private val extensions: Map<String, @Contextual Any>? = null
)