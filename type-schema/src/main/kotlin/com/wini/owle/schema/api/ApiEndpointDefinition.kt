package com.wini.owle.schema.api


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ApiEndpointDefinition(
    val id: String,
    val tags: Array<String> = []
) {

    companion object {
        const val QUALIFIED_NAME = "com.wini.owle.schema.api.ApiEndpointDefinition"
    }
}