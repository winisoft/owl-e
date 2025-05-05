package com.wini.owle.schema

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

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val models = resolver
            .getSymbolsWithAnnotation(OwleModel::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
        logger.info("Found ${models.count()} @OwleModel class declarations.")

        models.forEach { modelClass ->
            val dtoName = modelClass.simpleName.asString()
            val packageName = modelClass.packageName.asString()

            val dtoDoc = buildDtoDoc(modelClass)

            val file = codeGenerator.createNewFile(
                Dependencies(false, modelClass.containingFile!!),
                packageName,
                "${dtoName}Doc",
                "md"
            )

            file.bufferedWriter().use { writer ->
                renderDtoDoc(dtoDoc, writer)
            }
        }

        return emptyList()
    }

    private fun buildDtoDoc(modelClass: KSClassDeclaration): DtoModelDoc {
        val name = modelClass.simpleName.asString()
        val typeDoc = modelClass.docString?.trim() ?: "No type-level documentation."
        logger.info("$name has ${modelClass.getAllProperties().count()} properties")

        val properties = modelClass.getAllProperties().map { prop ->
            val propName = prop.simpleName.asString()
            val type = prop.type.resolve().declaration.qualifiedName?.asString() ?: "Unknown"
            val doc = prop.docString?.trim() ?: "No documentation."

            val constraints = prop.annotations.mapNotNull { annotation ->
                val annDecl = annotation.annotationType.resolve().declaration as? KSClassDeclaration ?: return@mapNotNull null
                val errorCode = annotation.arguments.firstOrNull { it.name?.asString() == "errorCode" }?.value as? String ?: return@mapNotNull null
                val message = annotation.arguments.firstOrNull { it.name?.asString() == "message" }?.value as? String

                val attrMap = annotation.arguments.associate { arg ->
                    arg.name?.asString() to arg.value
                }.filterKeys { it != "errorCode" && it != "message" }

                OwleErrorDoc(
                    errorCode = errorCode,
                    message = message ?: "$propName violates $errorCode",
                    attributes = emptyMap() //attrMap
                )
            }

            ModelPropertyDoc(
                name = propName,
                type = type,
                description = doc,
                constraints = constraints.toList()
            )
        }

        return DtoModelDoc(name, typeDoc, properties.toList())
    }

    private fun renderDtoDoc(dto: DtoModelDoc, writer: Appendable) {
        writer.appendLine("# ${dto.name}")
        writer.appendLine("---")
        writer.appendLine("> ${dto.description}")
        writer.appendLine()
        writer.appendLine("### Properties:")
        writer.appendLine()

        for (prop in dto.properties) {
            writer.appendLine("- #### **${prop.name}**: `${prop.type}`")
            writer.appendLine("\t${prop.description}")

            if (prop.constraints.isNotEmpty()) {
                writer.appendLine("\t- constraints:")
                for (constraint in prop.constraints) {
                    writer.appendLine("\t\t- `${constraint.errorCode}`:")
                    writer.appendLine("\t\t\t${constraint.message}")
                    for ((key, value) in constraint.attributes) {
                        writer.appendLine("\t\t\t- $key: $value")
                    }
                }
            }

            writer.appendLine()
        }
    }
}

class ModelSchemaProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor = ModelSchemaProcessor(
        environment.codeGenerator,
        environment.logger
    )
}