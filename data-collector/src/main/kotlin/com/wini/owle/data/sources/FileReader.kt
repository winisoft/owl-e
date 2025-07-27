package com.wini.owle.data.sources

import com.wini.owle.data.Constants.CREATE_SCRIPT_PATH
import com.wini.owle.data.Constants.DOWNLOADS_CSV_DIR
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.bufferedReader
import kotlin.io.useLines
import kotlin.streams.toList
import kotlin.use


object FileReader {

    var inputStream: InputStream? = FileReader::class.java.getResourceAsStream(CREATE_SCRIPT_PATH)

    fun getCreateScript(): List<String> {
        return inputStream?.bufferedReader().use { csvFile ->
            csvFile?.lines()?.toList()
        } ?: emptyList()
    }

    fun allVersionStrings(): List<String> {

        val versionStrings: MutableSet<String> = mutableSetOf()

        repeat(7) { i ->
            FileReader::class.java
                .getResourceAsStream("$DOWNLOADS_CSV_DIR/${i}000000.csv")?.use { stream ->
                    stream.bufferedReader().use { csvFile ->
                        csvFile.lines()?.toList()?.drop(1)?.map {
                            it.split(',').run {
                                if (count() > 1) get(1) else throw Exception()
                            }.apply { versionStrings.add(this) }
                        }
                    }
                }
        }

        return versionStrings.toList()
    }
}

//return versionStrings
//        Files.list(path).use { csvFiles ->
//            csvFiles
//                .filter { it.toString().endsWith(".csv") }
//                .sorted()
//                .map { csv ->
//                    Files.newBufferedReader(csv).useLines { lines ->
//                        lines.drop(1) // skip header row
//                            .forEach { line ->
//                                // CSV schema: project,version,download_count
//                                val parts = line.split(',')
//                                if (parts.size >= 2) {
//                                    versionStrings.add(parts[1])
//                                }
//                            }
//                    }
//                }
//        }
