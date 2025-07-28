package com.wini.owle.ksp.processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.doc.oas.ApiOperationDoc
import com.wini.owle.ksp.doc.oas.ApiRouteDoc
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.ksp.utils.ResponseBuilder
import com.wini.owle.ksp.utils.ext.findAnnotation
import com.wini.owle.ksp.utils.ext.get
import com.wini.owle.ksp.utils.ext.isOwleApiAnnotation
import com.wini.owle.schema.api.OwleApiOp


class ApiMethodProcessor(
    private val resolver: Resolver,
    private val logger: KSPLogger
) {

    fun processRoutes(declaration: KSClassDeclaration, basePath: String): List<ApiRouteDoc> {
        val routes = mutableMapOf<String, MutableList<ApiOperationDoc>>()

        declaration.getAllFunctions().forEach { function ->
            function.annotations
                .filter { it.isOwleApiAnnotation() }
                .forEach { annotation ->

                    val op = function.findAnnotation(OwleApiOp::class.qualifiedName!!)!!
                    val target = op.get("target")

                    val method = op.get("method").toString().lowercase().replace("com.wini.owle.schema.opmethod.", "")
                    val result = op.get("result").toString().lowercase().replace("com.wini.owle.schema.opresult.", "")

                    val keyBase = "$target.$method.$result"

                    val path = LocalizedStringResolver.resolve("$keyBase.path")
                    val summary = LocalizedStringResolver.resolve("$keyBase.summary")
                    val description = LocalizedStringResolver.resolve("$keyBase.description")

                    val responseType = determineResponseType(annotation)

                    val operation = ApiOperationDoc(
                        method = method,
                        summary = summary,
                        description = description,
                        responses = ResponseBuilder.build(method, responseType),
                        tags = emptyList()
                    )
                    routes.getOrPut(path) { mutableListOf() }.add(operation)
                }
        }

        return routes.map { (path, ops) -> ApiRouteDoc(path, ops) }
    }

    private fun determineResponseType(annotation: KSAnnotation): String? = when {
        annotation.shortName.asString().contains("Many") -> "array"
        annotation.shortName.asString().contains("One") -> "object"
        else -> null
    }

}
