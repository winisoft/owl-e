package com.wini.owle.air.api.operations

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentExtensions(
    @SerialName("x-is-form")
    val xIsForm: Boolean
)