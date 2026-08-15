package com.wini.owle.content.oas.info

import kotlinx.serialization.Serializable

/**
 * The OpenAPI `contact` object: who to reach about this API.
 */
@Serializable
data class Contact(
    val name: String? = null,
    val url: String? = null,
    val email: String? = null
)
