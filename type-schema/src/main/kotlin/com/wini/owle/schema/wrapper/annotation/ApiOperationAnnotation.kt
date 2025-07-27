package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation


/**
 * Represents an annotation applied to an API operation (e.g. a specific method like GET or POST).
 *
 * Encodes method-level metadata for routing and documentation purposes.
 */
interface ApiOperationAnnotation : KSAnnotation {

    /**
     * A string key identifying the localized or user-facing label for this operation.
     * Intended for use in internationalization or documentation.
     */
    val opResKey: String

    /**
     * A short, descriptive summary of the operation.
     */
    val summary: String

    /**
     * A longer-form description of the operation’s behavior and semantics.
     */
    val description: String

    /**
     * The relative path (beneath the endpoint’s base path) for this operation.
     * Example: "/{id}/details"
     */
    val path: String

    /**
     * The HTTP method used for this operation (e.g. "GET", "POST", "DELETE").
     */
    val httpMethod: String

    /**
     * A descriptor indicating expected cardinality.
     * Example values might include "single", "batch", or "stream".
     */
    val cardinality: String
}