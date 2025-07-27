package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.ksp.doc.oas.ApiOperationDoc
import com.wini.owle.ksp.doc.oas.ApiRouteDoc
import com.wini.owle.ksp.utils.ResponseBuilder
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration


class ApiMethodProcessor(
    private val logger: KSPLogger
) {

    fun processRoutes(declaration: ApiEndpointDeclaration): List<ApiRouteDoc> {

        val routes = mutableMapOf<String, MutableList<ApiOperationDoc>>()

        logger.warn("ApiMethodProcessor::processRoutes for Endpoint [${declaration.simpleName.asString()}]")

        declaration.operations.forEach { operationDeclaration ->
            val opAnnotation = operationDeclaration.apiOpsAnnotation

            val path = opAnnotation.path
            val summary = opAnnotation.summary
            val description = opAnnotation.description
            val httpMethod = opAnnotation.httpMethod
            val cardinality = opAnnotation.cardinality

            val responseType = when {
                cardinality.equals("many", ignoreCase = true) -> "array"
                cardinality.equals("one", ignoreCase = true) -> "object"
                else -> null
            }
            if (responseType == null) {
                logger.warn("Unknown cardinality '${cardinality}' for operation ${opAnnotation.opResKey}. Defaulting response type.")
            }

            val operationDoc = ApiOperationDoc(
                method = httpMethod,
                summary = summary,
                description = description,
                responses = ResponseBuilder.build(httpMethod, responseType),
                tags = emptyList()
            )
            routes.getOrPut(path) { mutableListOf() }.add(operationDoc)
        }

        return routes.map { (pathString, ops) -> ApiRouteDoc(pathString, ops) }
    }

}
