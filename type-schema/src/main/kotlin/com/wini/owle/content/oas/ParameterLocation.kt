package com.wini.owle.ksp.doc.oas

import kotlinx.serialization.Serializable

/**
 * Represents the location of a parameter in an API request.
 */
@Serializable
enum class ParameterLocation {
    PATH, QUERY, HEADER, COOKIE
}