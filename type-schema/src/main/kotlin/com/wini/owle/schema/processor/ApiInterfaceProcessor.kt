package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.doc.oas.ApiEndpointDoc
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.ksp.utils.ext.findAnnotation
import com.wini.owle.ksp.utils.ext.get
import com.wini.owle.schema.api.OwleApiSpec


/**
 * Processes a Ktor API endpoint class annotated with `@OwleApiSpec` to extract
 * OpenAPI-style documentation metadata.
 *
 * This includes:
 * - Base path and description from the annotation
 * - Tags defined on the endpoint
 * - DTO type for request/response modeling
 * - All routes and operations declared within the endpoint class
 *
 * The processor delegates method-level inspection to [ApiMethodProcessor].
 *
 * @param resolver the KSP resolver used for type and symbol resolution
 * @param logger used to report warnings or errors during processing
 * @param models a shared registry to track DTO model classes across endpoints
 */
class ApiInterfaceProcessor(
    private val resolver: Resolver,
    private val logger: KSPLogger,
    private val models: Map<String, KSClassDeclaration>
) {

    fun process(declaration: KSClassDeclaration): ApiEndpointDoc? {

        val apiSpecAnnotation: KSAnnotation? = declaration.findAnnotation(OwleApiSpec::class.qualifiedName!!)
        val specId = apiSpecAnnotation?.get("id") ?: error("Missing id on @OwleApiSpec")
        val tags = (apiSpecAnnotation.get("tags") as? List<*>)?.filterIsInstance<String>().orEmpty()

        val basePath = LocalizedStringResolver.resolve("$specId.basePath")
        val dtoType = LocalizedStringResolver.resolve("$specId.dtoType")

        val dtoClass = models[dtoType] ?: error("Model not found: $dtoType")
        val description = LocalizedStringResolver.resolve("$specId.description")

        val methodProcessor = ApiMethodProcessor(resolver, logger)
        val routes = methodProcessor.processRoutes(declaration, basePath)

        return ApiEndpointDoc(
            name = declaration.simpleName.asString(),
            description = description,
            basePath = basePath,
            routes = routes,
            dtoType = dtoClass.qualifiedName?.asString() ?: "",
            tags = tags
        )
    }

}
