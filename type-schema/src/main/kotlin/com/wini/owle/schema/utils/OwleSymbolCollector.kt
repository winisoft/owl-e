package com.wini.owle.ksp.utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.utils.ext.isResolved
import com.wini.owle.ksp.utils.ext.isSubclassOf
import com.wini.owle.schema.api.OwleApiSpec
import com.wini.owle.schema.error.OwleDocsError
import com.wini.owle.schema.OwleModel


object OwleSymbolCollector {

    private val modelClassDeclarations = mutableSetOf<KSClassDeclaration>()
    private val apiClassDeclarations = mutableSetOf<KSClassDeclaration>()
    private val errorClassDeclarations = mutableSetOf<KSClassDeclaration>()

    private var modelDone = false
    private var specsDone = false
    private var errorsDone = false

    private val modelFqName = OwleModel::class.qualifiedName!!
    private val apiSpecFqName = OwleApiSpec::class.qualifiedName!!
    private val errorFqName = OwleDocsError::class.qualifiedName!!


    /**
     * Finds all @OwleModel-annotated classes that are assignable to BaseDto.
     */
    fun collect(resolver: Resolver, logger: KSPLogger)  {

        if (!modelDone) {
            resolver.getSymbolsWithAnnotation(modelFqName)
                .filterIsInstance<KSClassDeclaration>()
                .filter { it.isSubclassOf("com.wini.owle.dto.BaseDto", resolver) }
                .toList()
                .apply {
                    modelClassDeclarations += this
                    modelDone = all { it.isResolved(resolver) }
                }
        }

        if (!specsDone) {
            resolver.getSymbolsWithAnnotation(apiSpecFqName)
                .filterIsInstance<KSClassDeclaration>()
                .filter { it.classKind == ClassKind.INTERFACE }
                .filter { it.isSubclassOf("com.wini.owle.api.IOwleApi", resolver) }
                .toList()
                .apply {
                    apiClassDeclarations += this
                    specsDone = all { it.isResolved(resolver) }
                }
        }

        if (!errorsDone) {
            resolver.getSymbolsWithAnnotation(errorFqName)
                .filterIsInstance<KSClassDeclaration>()
                .toList()
                .apply {
                    errorClassDeclarations += this
                    specsDone = all { it.isResolved(resolver) }
                }
        }


    }

    fun ready(): Boolean = modelDone && specsDone && errorsDone

    fun getModels(): List<KSClassDeclaration> = modelClassDeclarations.toList()
    fun getSpecs(): List<KSClassDeclaration> = apiClassDeclarations.toList()
    fun getErrors(): List<KSClassDeclaration> = errorClassDeclarations.toList()

    fun clear() {
        modelClassDeclarations.clear()
        apiClassDeclarations.clear()
        errorClassDeclarations.clear()
        modelDone = false
        specsDone = false
        errorsDone = false
    }

}
