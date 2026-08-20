package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation

/**
 * Represents an annotation marking a class as an API endpoint.
 *
 * Used to define high-level metadata for grouping operations by resource or concept.
 */
interface ApiEndpointAnnotation : KSAnnotation {

    /**
     * Unique identifier for this endpoint, typically derived from the class name.
     */
    val id: String

    /**
     * A list of tags used for grouping this endpoint in generated documentation.
     */
    val tags: List<String>

    /**
     * A one-line summary of the endpoint’s purpose.
     */
    val summary: String

    /**
     * A longer-form description of the endpoint’s role and semantics.
     */
    val description: String

    /**
     * The base path under which all operations for this endpoint are registered.
     * For example, "/workflow" or "/user".
     */
    val basePath: String

    /**
     * The fully qualified name of the DTO type associated with this endpoint.
     * Used to derive schema information for operations.
     */
    val dtoType: String

    /** The DTO's simple name, taken from the declaration. */
    val dtoNameSingle: String

    /** Its plural - `s` unless the bundle overrides the word. */
    val dtoNamePlural: String
}







