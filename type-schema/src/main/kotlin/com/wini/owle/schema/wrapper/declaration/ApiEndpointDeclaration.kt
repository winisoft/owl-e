package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.schema.wrapper.annotation.ApiEndpointAnnotation

/**
 * Represents a class annotated with `@ApiEndpointDefinition`.
 *
 * This wraps a `KSClassDeclaration` and models a single API controller-style endpoint class.
 */
interface ApiEndpointDeclaration : KSClassDeclaration {

    /** Metadata parsed from the `@ApiEndpointDefinition` annotation. */
    val apiEndpointAnnotation: ApiEndpointAnnotation

    /** All API operation methods declared within this endpoint. */
    val operations: List<ApiOperationDeclaration>
}
