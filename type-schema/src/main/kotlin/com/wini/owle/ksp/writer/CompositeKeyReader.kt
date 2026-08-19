package com.wini.owle.ksp.writer

import java.io.File


/**
 * Reads each `CompositeIdTable`'s key columns out of the Exposed table sources.
 *
 * KSP exposes declarations, not initialiser expressions, so `override val primaryKey =
 * PrimaryKey(step, tag)` is not reachable through the symbol API — the property is visible but the
 * columns it names are not. The declaration is a literal in a source file this module owns, so it
 * is read as text.
 *
 * That is narrower than it sounds. Only two shapes are matched, both of which the table sources
 * use uniformly, and a table whose key cannot be read is reported rather than guessed at.
 */
internal object CompositeKeyReader {

    data class Column(val property: String, val dbName: String, val referencedTable: String?)

    data class Table(
        val objectName: String,
        val tableName: String,
        /** Key column properties, in the order the primary key declares them. */
        val keyProperties: List<String>,
        val columns: Map<String, Column>
    ) {
        val keyColumns: List<Column> get() = keyProperties.mapNotNull { columns[it] }
    }

    private val TABLE = Regex("""object (\w+)\s*:\s*(?:Owle)?CompositeIdTable\("(\w+)"\)\s*\{""")
    private val REFERENCE = Regex("""val (\w+)\s*=\s*(?:key)?[Rr]eference\("(\w+)",\s*(\w+)[^)]*\)""")
    // the optional type-argument list matches `dbEnum<VersionOperator>("operator", 16)`; without
    // it the key columns of the specifier tables read as absent and their delegates went ungenerated
    private val PLAIN = Regex("""val (\w+)\s*=\s*\w+(?:<[^>]*>)?\("(\w+)"""")
    private val PRIMARY_KEY = Regex("""primaryKey\s*=\s*PrimaryKey\(([^)]*)\)""")

    /** Every composite table declared in [sourceFiles], keyed by its object name. */
    fun read(sourceFiles: Collection<String>): Map<String, Table> = buildMap {
        sourceFiles.map(::File).filter { it.isFile }.forEach { file ->
            val text = runCatching { file.readText() }.getOrNull() ?: return@forEach
            TABLE.findAll(text).forEach { match ->
                val body = bodyOf(text, match.range.last)
                val columns = buildMap {
                    REFERENCE.findAll(body).forEach {
                        put(it.groupValues[1], Column(it.groupValues[1], it.groupValues[2], it.groupValues[3]))
                    }
                    PLAIN.findAll(body).forEach {
                        putIfAbsent(it.groupValues[1], Column(it.groupValues[1], it.groupValues[2], null))
                    }
                }
                val keys = PRIMARY_KEY.find(body)
                    ?.groupValues?.get(1)
                    ?.split(",")
                    ?.map { it.trim() }
                    ?.filter { it.isNotEmpty() && !it.startsWith("name") }
                    .orEmpty()
                put(
                    match.groupValues[1],
                    Table(match.groupValues[1], match.groupValues[2], keys, columns)
                )
            }
        }
    }

    /** The braced body following [openBraceIndex], matched by depth so nested braces are kept. */
    private fun bodyOf(text: String, openBraceIndex: Int): String {
        var depth = 0
        var i = openBraceIndex
        while (i < text.length) {
            when (text[i]) {
                '{' -> depth++
                '}' -> {
                    depth--
                    if (depth == 0) return text.substring(openBraceIndex + 1, i)
                }
            }
            i++
        }
        return ""
    }
}
