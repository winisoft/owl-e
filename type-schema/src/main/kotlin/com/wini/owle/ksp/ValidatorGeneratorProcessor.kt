package com.wini.owle.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wini.owle.ksp.utils.OwleSymbolCollector
import com.wini.owle.ksp.utils.ext.isValidatorGenTarget
import com.wini.owle.ksp.writer.ValidatorFileWriter
import com.wini.owle.ksp.writer.ValidatorModuleFileWriter


class ValidatorGeneratorProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var hasGenerated = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (!hasGenerated) {

            logger.warn("Collecting for ValidatorGeneratorProcessor")
            OwleSymbolCollector.collect(resolver, logger)

            val models = OwleSymbolCollector.getModels()
            val errors = OwleSymbolCollector.getErrors()

            models.filter {
                it.isValidatorGenTarget
            }.forEach { modelClass ->
                try {
                    ValidatorFileWriter(codeGenerator, logger).generateValidator(modelClass)
                } catch (e: Exception) {
                    logger.error("Error generating validator for ${modelClass.simpleName.asString()}: ${e.message}")
                    throw e
                }
            }

            ValidatorModuleFileWriter(codeGenerator, logger).generate(models)

            hasGenerated = true
        }

        return emptyList()
    }

}

/**
 * Provider for the ValidatorGeneratorProcessor
 */
class ValidatorGeneratorProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return ValidatorGeneratorProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}
