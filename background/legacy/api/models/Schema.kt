package com.wini.owle.air.api.models

import com.wini.owle.air.api.models.Example
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Schema(
    val title: String? = null,
    val type: String? = null,
    val description: String? = null,
    val example: Example? = null,
    val properties: Map<String, Property>? = null,
    val required: List<String>? = null,
)