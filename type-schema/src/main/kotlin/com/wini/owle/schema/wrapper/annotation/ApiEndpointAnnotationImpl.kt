package com.wini.owle.schema.wrapper.annotation

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.wini.owle.ksp.utils.LocalizedStringResolver

/**
 * Represents an annotation marking a class as an API endpoint.
 *
 * Used to define high-level metadata for grouping operations by resource or concept.
 */
data class ApiEndpointAnnotationImpl(
    val delegate: KSAnnotation
): ApiEndpointAnnotation, KSAnnotation by delegate {

    override val id: String = delegate.arguments.first { ksValue ->
        ksValue.name?.asString() == "id"
    }.value.toString()

    override val tags: List<String> = delegate.arguments.find { it.name?.asString() == "tags" }?.value.let { argValue ->
        if (argValue is List<*>) {
            argValue.mapNotNull { elem ->
                (elem as? KSType)?.declaration?.qualifiedName?.asString()
            }
        } else {
            // Log a warning if 'tags' is present but not a list?
            // if (argValue != null) logger.warn("Annotation argument 'tags' was not a List for id '$id'")
            emptyList()
        }
    }

    override val dtoType: String = LocalizedStringResolver.resolve("$id.dtoType")

    override val basePath: String = LocalizedStringResolver.resolve("$id.basePath")

    override val summary: String = LocalizedStringResolver.resolve("$id.summary")

    override val description: String = LocalizedStringResolver.resolve("$id.description")

}