package com.wini.owle.content.oas

import kotlinx.serialization.Serializable

/**
 * The OpenAPI `tag` object: a named grouping that operations can be filed under.
 *
 * Named `OasTag` rather than `Tag` to avoid colliding with the Owl-E domain's own
 * `Tag` DTO, which is an unrelated user-facing concept.
 */
@Serializable
data class OasTag(
    val name: String,
    val description: String? = null
)
