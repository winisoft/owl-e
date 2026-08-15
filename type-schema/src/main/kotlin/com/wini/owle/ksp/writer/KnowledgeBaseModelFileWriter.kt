package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.content.doc.DtoModelDoc
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator


class KnowledgeBaseModelFileWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generate(model: KSClassDeclaration, doc: DtoModelDoc) {

        codeGenerator.createNewFile(
            dependencies = Dependencies(false, model.containingFile!!),
            packageName = model.packageName.asString(),
            fileName = "${model.simpleName.asString()}Doc",
            extensionName = "md"
        ).bufferedWriter().use { writer ->

            writer.appendLine(doc.name)
            writer.appendLine("---")
            writer.appendLine("> ${doc.description}")
            writer.appendLine()
            writer.appendLine("### Properties:")
            writer.appendLine()

            for (prop in doc.properties) {
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

}

