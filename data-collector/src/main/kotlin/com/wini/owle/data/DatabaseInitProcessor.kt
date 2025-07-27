package com.wini.owle.data

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wini.owle.data.Constants.DB_NAME
import com.wini.owle.data.generator.CreationScript
import com.wini.owle.data.generator.Distributions
import com.wini.owle.data.generator.PythonVersions


class DatabaseInitProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {


    override fun process(resolver: Resolver): List<KSAnnotated> {

        generateInitialSql(codeGenerator, logger)

        return emptyList()

    }

    /**
     * Generates the initial body of SQL scripts that will initialize the Owl-E DB
     */
    fun generateInitialSql(
        codeGenerator: CodeGenerator,
        logger: KSPLogger
    ) {
        var index = 0

        index += CreationScript(codeGenerator, logger).generate(index, DB_NAME)

        index += Distributions(codeGenerator, logger).generateInitial(index)

        index += PythonVersions(codeGenerator, logger).generateInitial(index)

    }


}


/**
 * Provider for the ModelSchemaProcessor
 */
class DatabaseInitProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return DatabaseInitProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}