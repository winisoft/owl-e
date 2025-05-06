package com.wini.owle.content.decorator



/**
 * Marks a class as an API endpoint with documentation.
 *
 * This annotation allows specifying tags and other metadata for OpenAPI documentation.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class OwleApiEndpoint(
    val description: String = "",
    val tags: Array<String> = []
)