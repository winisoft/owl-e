package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.content.oas.ApiEndpointDoc
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration


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

    fun process(declaration: ApiEndpointDeclaration): ApiEndpointDoc? {

        val methodProcessor = ApiMethodProcessor(logger)
        val routes = methodProcessor.processRoutes(declaration)

        return declaration.apiEndpointAnnotation.run {
            ApiEndpointDoc(
                name = declaration.simpleName.asString().toString(),
                description = description,
                basePath = basePath,
                routes = routes,
                dtoType = dtoType,
                tags = tags
            )
        }
    }

}
