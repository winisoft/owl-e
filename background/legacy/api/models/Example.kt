package com.wini.owle.air.api.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Example(
    val summary: String,
    val description: String,
    @Contextual
    val value: Any,
    val externalValue: String,
    @SerialName("${'$'}ref")
    val ref: String,
    val extensions: Map<String, @Contextual Any>,
    val valueSetFlag: Boolean
)
