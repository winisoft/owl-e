package com.wini.owle.content.oas.info

import kotlinx.serialization.Serializable

/**
 * The OpenAPI `license` object: the license the API is published under.
 */
@Serializable
data class License(
    val name: String = "",
    val url: String? = null
)
