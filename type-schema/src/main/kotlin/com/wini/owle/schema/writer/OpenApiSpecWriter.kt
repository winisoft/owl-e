package com.wini.owle.ksp.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.wini.owle.ksp.doc.oas.ApiEndpointDoc
import com.wini.owle.ksp.doc.oas.OpenApiSpec
import com.wini.owle.ksp.processor.fromEndpoints
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.OutputStreamWriter

/**
 * Writes the OpenAPI specification to a generated JSON file using the KSP code generator.
 */
object OpenApiSpecWriter {

    fun writeSpec(endpoints: List<ApiEndpointDoc>, codeGenerator: CodeGenerator) {
        val openApiSpec = OpenApiSpec.fromEndpoints(endpoints)
        val json = Json { prettyPrint = true; encodeDefaults = true }
        val jsonString = json.encodeToString(openApiSpec)

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.wini.owle.openapi",
            fileName = "owle-oas",
            extensionName = "json"
        )

        OutputStreamWriter(file, Charsets.UTF_8).use {
            it.write(jsonString)
        }
    }

}