package com.wini.owle.schema.wrapper.declaration

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.wini.owle.schema.wrapper.annotation.ApiOperationAnnotation


/**
 * Represents a function in a KSP-processed class that is annotated with an API operation definition.
 *
 * This interface wraps a `KSFunctionDeclaration` to expose additional information derived from the associated annotation.
 */
interface ApiOperationDeclaration : KSFunctionDeclaration {

    /** Parsed metadata from the `@ApiOperationDefinition` annotation. */
    val apiOpsAnnotation: ApiOperationAnnotation

    /** A unique identifier for this operation, used as a lookup key for response metadata. */
    val opResKey: String

    /** A brief summary suitable for OpenAPI generation or UI display. */
    val summary: String

    /** A longer description of the operation. */
    val description: String

    /** The API path associated with this operation. */
    val path: String
}
