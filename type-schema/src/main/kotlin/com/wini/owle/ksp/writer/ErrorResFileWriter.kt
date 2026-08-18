package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.ksp.utils.LocalizedStringResolver
import com.wini.owle.schema.wrapper.declaration.error.ErrorDeclaration


class ErrorResFileWriter(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generate(errors: List<ErrorDeclaration>) {

        val packageName = "com.wini.owle.error"
        val fileName = "ErrorResourceExtensions"

        val resolved = errors.map { error ->
            val key = error.errorAnnotation.errorResKey
            Triple(
                error.simpleName.asString(),
                error.code,
                LocalizedStringResolver.resolve("$key.message")
            ) to (error.typeParameters.isNotEmpty() to key)
        }

        resolved.forEach { (triple, meta) ->
            val (name, code, message) = triple
            val (_, key) = meta
            if (!LocalizedStringResolver.isBound("$key.code") || !LocalizedStringResolver.isBound("$key.message")) {
                logger.warn(
                    "[error-res] $name has no resource bound at '$key'; docres_en_US.properties " +
                        "needs '$key.code' and '$key.message'. Emitting the key as the value."
                )
            }
            check(code.isNotBlank() && message.isNotBlank()) { "$name resolved to a blank code or message" }
        }

        codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = packageName,
            fileName = fileName
        ).bufferedWriter().use { writer ->
            writer.appendLine("package $packageName")
            writer.appendLine()
            writer.appendLine("/**")
            writer.appendLine(" * Generated from the @ErrorDefinition keys and docres_en_US.properties. Do not edit.")
            writer.appendLine(" *")
            writer.appendLine(" * [owleErrorResources] is what backs the default `errorCode` / `message` on OwleError,")
            writer.appendLine(" * so an error type states its identity once - in its annotation - and the wording stays")
            writer.appendLine(" * in the resource bundle where it can be translated.")
            writer.appendLine(" */")
            writer.appendLine("val owleErrorResources: Map<String, Pair<String, String>> = mapOf(")
            resolved.sortedBy { it.first.first }.forEach { (triple, _) ->
                val (name, code, message) = triple
                writer.appendLine("    ${name.quote()} to (${code.quote()} to ${message.quote()}),")
            }
            writer.appendLine(")")
            writer.appendLine()
            writer.appendLine("/** The published code for an error class, or an empty string when it declares none. */")
            writer.appendLine("fun owleErrorCodeOf(simpleName: String?): String = owleErrorResources[simpleName]?.first ?: \"\"")
            writer.appendLine()
            writer.appendLine("/** The published message for an error class, or an empty string when it declares none. */")
            writer.appendLine("fun owleErrorMessageOf(simpleName: String?): String = owleErrorResources[simpleName]?.second ?: \"\"")

            resolved.forEach { (triple, meta) ->
                val (name, code, message) = triple
                val (generic, _) = meta
                // A star projection accepts every instantiation, so callers holding a
                // `PositiveValueIntError<Step>` need not name the type argument.
                val receiver = if (generic) "$name<*>" else name
                writer.appendLine()
                writer.appendLine("fun $receiver.errorResCode(): String = ${code.quote()}")
                writer.appendLine("fun $receiver.errorResMsg(): String = ${message.quote()}")
            }
        }
    }

    private fun String.quote(): String = "\"${this.replace("\"", "\\\"")}\""

}