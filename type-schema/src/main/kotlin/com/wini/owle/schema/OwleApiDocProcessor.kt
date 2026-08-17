package com.wini.owle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.wini.owle.ksp.processor.ApiInterfaceProcessor
import com.wini.owle.ksp.writer.OpenApiSpecFileWriter
import com.wini.owle.ksp.utils.OwleSymbolCollector
import com.wini.owle.schema.wrapper.declaration.ApiEndpointDeclaration
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration


class OwleApiDocProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var hasGenerated: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (hasGenerated)
            return emptyList()

        logger.warn("Collecting for ModelSchemaProcessor")

        OwleSymbolCollector.collect(resolver, logger) { (models, errors, endpoints) ->
            processSpec(endpoints, models, errors, resolver, logger)
        }

        hasGenerated = true

        return emptyList()
    }

    private fun processSpec(
        specs: List<ApiEndpointDeclaration>,
        models: List<ModelDeclaration>,
        errors: List<ErrorDeclaration>,
        resolver: Resolver,
        logger: KSPLogger,
    ) {

        val modelsByQualifiedNames = models.associateBy { it.qualifiedName?.asString()!! }

        ApiInterfaceProcessor(resolver, logger, modelsByQualifiedNames)
            .run {
                specs.mapNotNull { process(it) }
            }.also { it ->
                OpenApiSpecFileWriter(codeGenerator, logger).writeSpec(it)
            }

    }

}

class OwleApiDocProcessorProvider : SymbolProcessorProvider {
    /** DTO-derived: generates into `common`. */
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        environment.processorFor(OwleTarget.COMMON) {
            OwleApiDocProcessor(
                environment.codeGenerator,
                environment.logger
            )
        }
}