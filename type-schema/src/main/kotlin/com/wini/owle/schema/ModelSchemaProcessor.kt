package com.wini.owle.ksp

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.wini.owle.content.doc.DtoModelDoc
import com.wini.owle.content.doc.ModelPropertyDoc
import com.wini.owle.content.doc.OwleErrorDoc
import com.wini.owle.ksp.utils.OwleSymbolCollector
import com.wini.owle.ksp.utils.ext.codeAndMessage
import com.wini.owle.ksp.writer.ErrorResFileWriter
import com.wini.owle.ksp.writer.KnowledgeBaseErrorFileWriter
import com.wini.owle.ksp.writer.ReferenceSiteWriter
import com.wini.owle.ksp.writer.KnowledgeBaseModelFileWriter
import com.wini.owle.schema.wrapper.annotation.error.FieldConstraintAnnotation
import com.wini.owle.schema.wrapper.declaration.ModelDeclaration
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration

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

        if (hasGenerated)
            return emptyList()

        OwleSymbolCollector.collect(resolver, logger) { (models, errors, _) ->
            logger.warn("Preparing generate res: ${errors.size} errors")

            ErrorResFileWriter(codeGenerator, logger).generate(errors)

            val docs = models.map { it to buildDtoDoc(it, errors) }

            KnowledgeBaseModelFileWriter(codeGenerator, logger).apply {
                docs.forEach { (cd, doc) -> generate(cd, doc) }
            }

            KnowledgeBaseErrorFileWriter(codeGenerator, logger)
                .generate(errors, docs.map { (_, doc) -> doc })

            ReferenceSiteWriter(codeGenerator, logger).generate(
                models = docs.map { (cd, doc) -> ReferenceSiteWriter.ModelEntry(cd, doc) },
                errors = errors,
                endpointAnnotationOf = ::endpointAnnotationOf
            )
        }

        hasGenerated = true

        return emptyList()
    }

    /**
     * One [ModelPropertyDoc] per property on [modelClass] — every property, not only the
     * constrained ones, so the generated doc is a complete description of the type. A
     * property's [ModelPropertyDoc.constraints] carries the real, resolved [OwleErrorDoc] for
     * each of its `@Constraint*` annotations; an FQN with no matching `@ErrorDefinition` class
     * (e.g. a constraint referencing an error type that was never written) is dropped rather
     * than surfaced, matching how [com.wini.owle.ksp.utils.ConstraintExtractor] already treats
     * unresolvable error references in the validator-generation path.
     */
    private fun buildDtoDoc(
        modelClass: ModelDeclaration,
        errors: List<ErrorDeclaration>
    ): DtoModelDoc {

        val name = modelClass.simpleName.asString()
        val typeDoc = modelClass.docString?.trim() ?: "No type-level documentation."

        val constraintsByPropName: Map<String, List<FieldConstraintAnnotation>> =
            modelClass.constrainedFields.entries.associate { entry ->
                entry.key.simpleName.asString() to entry.value
            }

        val properties = modelClass.getAllProperties().map { prop ->
            val propName = prop.simpleName.asString()
            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
            val doc = prop.docString?.trim() ?: "No documentation."

            val constraintDocs = (constraintsByPropName[propName] ?: emptyList())
                .flatMap { it.errorFqNames }
                .mapNotNull { errors.codeAndMessage(it) }
                .map { (code, msg) -> OwleErrorDoc(errorCode = code, message = msg) }

            ModelPropertyDoc(
                name = propName,
                type = type,
                description = doc,
                constraints = constraintDocs
            )
        }.toList()

        logger.info("$name: documented ${properties.size} properties")

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

    /**
     * The `(id, tags, path)` a class declares via `@ApiEndpointDefinition`, or null when it
     * declares none. Read straight off the annotation rather than through the resource bundle:
     * the path is a literal in source, and routing must not depend on a lookup that can miss.
     */
    private fun endpointAnnotationOf(
        declaration: KSClassDeclaration
    ): Triple<String, List<String>, String>? {
        val anno = declaration.annotations.firstOrNull {
            it.annotationType.resolve().declaration.qualifiedName?.asString() ==
                "com.wini.owle.schema.api.ApiEndpointDefinition"
        } ?: return null

        fun arg(name: String): Any? = anno.arguments.firstOrNull { it.name?.asString() == name }?.value

        val id = arg("id") as? String ?: return null
        @Suppress("UNCHECKED_CAST")
        val tags = (arg("tags") as? List<Any?>)?.mapNotNull { it as? String }.orEmpty()
        val path = arg("path") as? String ?: ""
        return Triple(id, tags, path)
    }

}

class ModelSchemaProcessorProvider : SymbolProcessorProvider {
    /** DTO-derived: generates into `common`. */
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor = environment.processorFor(OwleTarget.COMMON) {
        ModelSchemaProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }


}