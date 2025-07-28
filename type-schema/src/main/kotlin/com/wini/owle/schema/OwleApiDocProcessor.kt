package com.wini.owle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.wini.owle.ksp.processor.ApiInterfaceProcessor
import com.wini.owle.ksp.writer.OpenApiSpecFileWriter
import com.wini.owle.ksp.utils.OwleSymbolCollector


class OwleApiDocProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var hasGenerated: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (!hasGenerated) {

            logger.warn("Collecting for ModelSchemaProcessor")

            OwleSymbolCollector.collect(resolver, logger)

            val models = OwleSymbolCollector.getModels()
            val specs = OwleSymbolCollector.getSpecs()
            val errors = OwleSymbolCollector.getErrors()

            // Now safe to proceed — all are resolved.
            processSpec(specs, models, errors, resolver, logger)

            hasGenerated = true
        }

        return emptyList()
    }

    private fun processSpec(
        specs: List<KSClassDeclaration>,
        models: List<KSClassDeclaration>,
        errors: List<KSClassDeclaration>,
        resolver: Resolver,
        logger: KSPLogger,
    ) {

        val modelsByQualifiedNames = models.associateBy { it.qualifiedName?.asString()!! }

        ApiInterfaceProcessor(resolver, logger, modelsByQualifiedNames)
            .run {
                specs.mapNotNull { process(it) }
            }.also { it ->
                OpenApiSpecFileWriter.writeSpec(it, codeGenerator)
            }

    }

}

class OwleApiDocProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return OwleApiDocProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}