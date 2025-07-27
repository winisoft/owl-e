package com.wini.owle.data.generator


import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.wini.owle.data.Constants.DB_NAME
import com.wini.owle.data.Constants.OUTPUT_DIR_SQL
import com.wini.owle.data.ext.createNewFile
import com.wini.owle.data.ext.fileName
import java.io.File
import java.io.OutputStreamWriter

/**
 * Manages the execution of multiple SQL scripts for a coordinated database initialization occurring across many files
 */
class InitScript(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) {

    fun generate(fileNames: List<String>) {
        val executionPath = "C:/Users/wini/owle/data-collector/build/generated/ksp/main/resources/com.wini.owle/$OUTPUT_DIR_SQL"
        val outfile = codeGenerator.createNewFile(
            dependencies = Dependencies(false),
            packageName = OUTPUT_DIR_SQL,
            fileName = "init",
            extensionName = "sql"
        )

        OutputStreamWriter(outfile).use { writer ->
            writer.write(
                buildString {
                    appendLine("DROP DATABASE IF EXISTS ${DB_NAME};")
                    appendLine("CREATE DATABASE ${DB_NAME};")
                    appendLine("source $executionPath/$OUTPUT_DIR_SQL/${fileNames[0]}")
                    appendLine()
                    appendLine("use $DB_NAME;")
                    appendLine()
                    appendLine("START TRANSACTION;")
                    appendLine()

                    fileNames.takeLast(fileNames.size - 1).forEach {
                        appendLine("source $executionPath/$OUTPUT_DIR_SQL/${it}")
                    }

                }
            )
        }

    }

}

//-- Begin a single transaction for inserts
//START TRANSACTION;
//
//-- Insert data
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/000_distributions_0.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/001_python_versions_0.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/002_python_versions_1.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/003_distribution_versions_0.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/004_distribution_versions_1.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/005_distribution_versions_2.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/006_distribution_versions_3.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/007_distribution_versions_4.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/008_distribution_versions_5.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/009_distribution_versions_6.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/010_distribution_versions_7.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/011_distribution_versions_8.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/012_distribution_versions_9.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/013_distribution_versions_10.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/014_distribution_versions_11.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/015_distribution_versions_12.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/016_distribution_versions_13.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/017_distribution_versions_14.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/018_distribution_versions_15.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/019_distribution_versions_16.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/020_distribution_versions_17.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/021_distribution_versions_18.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/022_distribution_versions_19.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/023_distribution_versions_20.sql
//source C:/Users/wini/Downloads/owl-e/server/src/main/resources/sql/init/024_transformations_0.sql
//
//-- If all inserts succeed
//COMMIT;