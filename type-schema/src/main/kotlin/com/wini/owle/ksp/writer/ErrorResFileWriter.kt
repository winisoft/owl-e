package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.ksp.utils.ext.get
import com.wini.owle.ksp.utils.ext.isOwleError


class ErrorResFileWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generate(errors: List<KSClassDeclaration>) {

        val packageName = "com.wini.owle.error"
        val fileName = "ErrorResourceExtensions"

        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = packageName,
            fileName = fileName
        ).bufferedWriter().use { writer ->
            writer.appendLine("package $packageName")
            writer.appendLine()
            writer.appendLine("import com.wini.owle.dto.BaseDto")

            for (error in errors) {
                val annotation = error.annotations.firstOrNull { it.isOwleError() } ?: continue
                val errorId = annotation.get("id")?.toString() ?: continue
                val simpleName = error.simpleName.asString()
                val code = LocalizedStringResolver.resolve("$errorId.code")
                val message = LocalizedStringResolver.resolve("$errorId.message")
                val genericSuffix = if (error.typeParameters.isNotEmpty()) "<out BaseDto>" else ""

                writer.appendLine()
                writer.appendLine("fun $simpleName$genericSuffix.errorResCode(): String = ${code.quote()}")
                writer.appendLine("fun $simpleName$genericSuffix.errorResMsg(): String = ${message.quote()}")
            }
        }
    }

    private fun String.quote(): String = "\"${this.replace("\"", "\\\"")}\""

}