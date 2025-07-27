package com.wini.owle.data.generator

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.data.Constants
import java.io.OutputStreamWriter

/**
 * Manages the generation of SQL for the 'distributions' table
 */
class Distributions(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generateInitial(execIteration: Int): Int {

        var id = 1
        val batches = getAllDistributions().chunked(CHUNK_SIZE)

        batches.forEachIndexed { tableIteration, batch ->
            codeGenerator.writeBatch(batch, execIteration, tableIteration, id)
            id += CHUNK_SIZE
        }

        return batches.size
    }

    private fun getAllDistributions(): List<String> {
        // fetch html from SIMPLE_INDEX_URL
        val response = ""

        // parse html : select <a>.text
        val distributionNames: List<String> = emptyList()

        return distributionNames
    }

    private fun filterUncommittedDistributions(simpleDists: List<String>): List<String> {
        // determine which among the simpleDists result are not in the db
        // filter to only those we need to update the db with an insert
        return simpleDists
    }

    /**
     * Writes or appends SQL insert statements for the provided package names.
     */
    private fun CodeGenerator.writeBatch(
        packageNames: List<String>,
        execIteration: Int,
        tablePhase: Int,
        startId: Int
    ) {
        val outFile = createNewFile(
            dependencies = Dependencies(false),
            packageName = "com.wini.owle.db",
            fileName = "${execIteration}_distributions_${tablePhase}",
            extensionName = "sql"
        )

        OutputStreamWriter(outFile, Charsets.UTF_8).use {
            it.write(
                buildString {
                    appendLine("INSERT INTO `${Constants.DB_NAME}`.`${TABLE_NAME}` (id, name) VALUES")
                    packageNames.forEachIndexed { index, name ->
                        appendLine("(${startId + index}, '${name}'${if (index == packageNames.size-1) ")" else "),"}")
                    }
                }
            )
        }
    }

    companion object {
        const val CHUNK_SIZE = 25000
        const val SIMPLE_INDEX_URL = "https://pypi.org/simple/"
        const val TABLE_NAME = "distributions"
    }
}