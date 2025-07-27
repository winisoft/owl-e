package com.wini.owle.data.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.data.ext.createNewFile
import com.wini.owle.data.sources.FileReader
import io.ktor.server.http.content.resourceClasspathResource
import java.io.File
import java.io.OutputStreamWriter

/**
 * Manages the generation of SQL for the 'distributions' table
 */
class CreationScript(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    /**
     * Copies the prewritten db creation script wholesale to the targeted output directory
     */
    fun generate(iteration: Int, dbName: String, dropIfExists: Boolean = true): Int {

        val inputFile = FileReader.getCreateScript()
        val outfile = codeGenerator.createNewFile("create", iteration, 0)

        OutputStreamWriter(outfile).use { writer ->
            writer.write(
                buildString {
                    if (dropIfExists) appendLine("DROP DATABASE IF EXISTS $dbName;")
                    appendLine("CREATE DATABASE $dbName;")
                    appendLine()
                    appendLine("USE $dbName;")
                    appendLine()
                    inputFile.forEach { appendLine(it)}
                }
            )
        }

        return 1
    }

}