package com.wini.owle.ksp.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.utils.ext.isResolved
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclarationImpl
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclarationImpl
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration
import com.wini.owle.schema.wrapper.declaration.ModelDeclarationImpl
import com.wini.owle.schema.api.ApiEndpointDefinition
import com.wini.owle.schema.error.ErrorDefinition
import com.wini.owle.schema.ModelDefinition

typealias Models = List<ModelDeclaration>
typealias Endpoints = List<ApiEndpointDeclaration>
typealias Errors = List<ErrorDeclaration>

object OwleSymbolCollector {

    private val modelDeclarations = mutableSetOf<ModelDeclaration>()
    private val apiEndpointDeclarations = mutableSetOf<ApiEndpointDeclaration>()
    private val errorDeclarations = mutableSetOf<ErrorDeclaration>()

    private var modelDone = false
    private var endpointsDone = false
    private var errorsDone = false

    fun collect(
        resolver: Resolver,
        logger: KSPLogger,
        onReady: (Triple<Models, Errors, Endpoints>) -> Unit
    ) {

        if (!modelDone) {
            resolver.getSymbolsWithAnnotation(ModelDefinition::class.qualifiedName!!)
                .filterIsInstance<KSClassDeclaration>()
                .toList()
                .apply {
                    modelDeclarations += map { ModelDeclarationImpl(it) }
                    modelDone = all { it.isResolved }
                        .also { if (it) logger.warn("Collected ${count()} models") }
                }
        }

        if (!endpointsDone) {
            resolver.getSymbolsWithAnnotation(ApiEndpointDefinition::class.qualifiedName!!)
                .filterIsInstance<KSClassDeclaration>()
                .toList()
                .apply {
                    apiEndpointDeclarations += map { ApiEndpointDeclarationImpl(it) }
                    endpointsDone = all { it.isResolved }
                        .also { if (it) logger.warn("Collected ${count()} endpoints") }
                }
        }

        if (!errorsDone) {
            resolver.getSymbolsWithAnnotation(ErrorDefinition::class.qualifiedName!!)
                .filterIsInstance<KSClassDeclaration>()
                .toList()
                .apply {
                    errorDeclarations += map { ErrorDeclarationImpl(it) }
                    endpointsDone = all { it.isResolved }
                        .also { if (it) logger.warn("Collected ${count()} errors") }
                }
        }

        onReady(Triple(modelDeclarations.toList(), errorDeclarations.toList(), apiEndpointDeclarations.toList()))

    }
}
