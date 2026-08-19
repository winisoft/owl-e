package com.wini.owle.schema.api


/**
 * Declares that a DTO is exposed as a REST resource.
 *
 * Applied directly to the `@ModelDefinition` DTO rather than to a separate marker interface:
 * the DTO already carries everything an endpoint needs — its serializer, its ID type (via
 * `ULongDto`/`CompositeDto`/`StringDto`), and its generated validator — so a hand-written
 * interface existing only to hold this annotation was pure ceremony.
 *
 * The eight CRUD operations are **implied**, not declared. Every endpoint has the same
 * {GET, POST, PUT, DELETE} × {ONE, MANY} set, and every `@ApiOperationDefinition` that used to
 * spell them out repeated the endpoint's own [id] as its key. Nothing was expressed by writing
 * them out that isn't recoverable from this one annotation.
 */
@Target(AnnotationTarget.CLASS)
// BINARY, not SOURCE: the endpoint classes are generated in `server`, where the DTOs arrive as
// compiled dependencies. Under SOURCE retention this annotation is discarded at compile time and
// server's KSP round cannot see it at all, so no route could be derived there.
@Retention(AnnotationRetention.BINARY)
annotation class ApiEndpointDefinition(
    val id: String,
    val tags: Array<String> = [],
    /** Route segment under `/api`. Leading slash optional — `IOwleApi.basePath` normalizes it. */
    val path: String = ""
) {

    companion object {
        const val QUALIFIED_NAME = "com.wini.owle.schema.api.ApiEndpointDefinition"
    }
}