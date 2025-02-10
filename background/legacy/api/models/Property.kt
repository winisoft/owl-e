package com.wini.owle.air.api.models

import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val type: String,
    val readOnly: Boolean? = false,
    val writeOnly: Boolean? = false,
    val enum: List<String>? = null,
    val properties: Map<String, Property>? = null
)