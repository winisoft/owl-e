package com.wini.owle.content.oas.info

import kotlinx.serialization.Serializable

/**
 * The OpenAPI `info` object: metadata describing the API as a whole.
 *
 * All fields carry defaults so an [Info] can be constructed empty and populated
 * from resource keys as the spec is assembled.
 */
@Serializable
data class Info(
    val title: String = "",
    val version: String = "",
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: Contact? = null,
    val license: License? = null
)
