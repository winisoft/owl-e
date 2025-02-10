package com.wini.owle.air.api.operations

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val contentExtensions: ContentExtensions,
    val contentType: String,
    val isForm: Boolean,
    val parameters: List<Param>
)