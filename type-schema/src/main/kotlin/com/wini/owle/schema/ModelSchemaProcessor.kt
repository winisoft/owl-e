package com.wini.owle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.doc.models.DtoModelDoc
import com.wini.owle.ksp.doc.models.ModelPropertyDoc
import com.wini.owle.ksp.doc.models.OwleErrorDoc
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.ksp.utils.OwleSymbolCollector
import com.wini.owle.ksp.utils.ext.filterIsFieldConstraint
import com.wini.owle.ksp.utils.ext.findConstraintRaisedErrorId
import com.wini.owle.ksp.utils.ext.isValidatorGenTarget
import com.wini.owle.ksp.writer.ErrorResFileWriter
import com.wini.owle.ksp.writer.KnowledgeBaseModelFileWriter


import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.wini.owle.content.doc.DtoModelDoc
import com.wini.owle.content.doc.ModelPropertyDoc
import com.wini.owle.content.doc.OwleErrorDoc
import com.wini.owle.ksp.OwleModel

/**
 * A symbol processor designed to facilitate the generation of code and documentation relevant to the Owl-e application. 
 * Its primary focus is upon the classes of the common module's 'dto' model types, which are the types the API trades in and
 * the only types relevant to users' interactions with the API or app as a whole.
 * 
 * It generates documentation for each of these model types, which depends on interpreting the annotations defining constraints 
 * on the properties of the model and the OwleErrors representing its constraints' violation.
 */
class ModelSchemaProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var hasGenerated: Boolean = false

    override fun process(resolver: Resolver): List<KSAnnotated> {

        if (!hasGenerated) {

            logger.warn("Collecting for ModelSchemaProcessor")

            OwleSymbolCollector.collect(resolver, logger)

            val models: List<KSClassDeclaration> = OwleSymbolCollector.getModels()
            val errors: List<KSClassDeclaration> = OwleSymbolCollector.getErrors()

            logger.warn("Preparing generate res: ${errors.size} errors")
            ErrorResFileWriter(codeGenerator, logger).generate(errors)

            KnowledgeBaseModelFileWriter(codeGenerator, logger).apply {
                models.map {
                    it to buildDtoDoc(it, errors)
                }.forEach { (cd, doc) ->
                    generate(cd, doc)
                }
            }

            hasGenerated = true
        }

        return emptyList()
    }

    private fun buildDtoDoc(
        modelClass: KSClassDeclaration,
        errors: List<KSClassDeclaration>
    ): DtoModelDoc {

        val name = modelClass.simpleName.asString()
        val typeDoc = modelClass.docString?.trim() ?: "No type-level documentation."
        logger.info("$name has ${modelClass.getAllProperties().count()} properties")

        val properties = modelClass.getAllProperties().map { prop ->
            val propName = prop.simpleName.asString()
            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
            val doc = prop.docString?.trim() ?: "No documentation."

            val constraints = prop.annotations.toList().filterIsFieldConstraint().map { ann ->

                val errorId = errors.findConstraintRaisedErrorId(ann)
                val code: String = LocalizedStringResolver.resolve("$errorId.code")
                val message: String = LocalizedStringResolver.resolve("$errorId.message")

                OwleErrorDoc(
                    errorCode = code,
                    message = message,
                    attributes = ann.arguments.associate { (it.name?.asString() ?: "?") to it.value }
                )
            }.toList()

            ModelPropertyDoc(
                name = propName,
                type = type,
                description = doc,
                constraints = constraints
            )
        }.toList()

        logger.warn("Making DtoModelDoc! name: $name, typeDoc: $typeDoc")
        for (property in properties) {
            logger.warn("         property: ${property.name.toString()}")
        }

        return DtoModelDoc(name, typeDoc, properties)
    }

//    private fun buildDtoDoc(
//        modelClass: KSClassDeclaration,
//        errors: List<KSClassDeclaration>
//    ): DtoModelDoc {
//
//        val name = modelClass.simpleName.asString()
//        val typeDoc = modelClass.docString?.trim() ?: "No type-level documentation."
//
//        val properties = modelClass.getAllProperties().map { prop ->
//            val propName = prop.simpleName.asString()
//            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
//            val doc = prop.docString?.trim() ?: "No documentation."
//
//            val propertyDocs: List<ModelPropertyDoc> = prop.annotations.mapNotNull { annotation ->
//
//                val errorType: KSType = annotation.arguments
//                    .firstOrNull { it.name?.asString() == "error" }
//                    ?.value as? KSType
//                    ?: return@mapNotNull null
//
//                val constraints = prop.annotations.map { ann ->
//
//                    val constraintAnnoSimpleName = ann.annotationType.resolve().declaration.simpleName.asString()
//
//                    val raisedTypeSimpleName = ConstraintRegistry.getErrorQualifiedName(constraintAnnoSimpleName)
//
//                    val raisedErrorId = errors.firstOrNull {
//                        it.simpleName.asString() == raisedTypeSimpleName
//                    }?.annotations
//                        ?.filter { it.isOwleError() }
//                        ?.firstOrNull()
//                        ?.get("id").toString()
//
//                    val code: String = LocalizedStringResolver.resolve("$raisedErrorId.code")
//                    val message: String = LocalizedStringResolver.resolve("$raisedErrorId.message")
//
//                    OwleErrorDoc(
//                        errorCode = code,
//                        message = message,
//                        attributes = ann.arguments.associate { (it.name?.asString() ?: "?") to it.value }
//                    )
//                }.toList()
//
//                ModelPropertyDoc(
//                    name = propName,
//                    type = type,
//                    description = doc,
//                    constraints = constraints
//                )
//
//            }.toList()
//
//        }
//
//    }

}

class ModelSchemaProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor = ModelSchemaProcessor(
        environment.codeGenerator,
        environment.logger
    )
}