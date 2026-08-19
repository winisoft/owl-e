package com.wini.owle.entity

import com.wini.owle.TestHelper
import com.wini.owle.dto.DbToken
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


/**
 * Every enum column, checked against the database's own definition.
 *
 * Twenty of twenty-nine columns were unreadable before this existed and nothing caught it: the
 * failure is per-value and happens at read time, so a column only breaks once a row carrying that
 * value is fetched, and most were never fetched by a test. `versions.phase` was the one that was,
 * and it is the only reason the rest came to light.
 *
 * Reading `information_schema` instead of restating the values keeps this from becoming a second
 * copy of the schema with a drift problem of its own.
 */
@DisplayName("Enum column drift")
abstract class EnumColumnDriftTest {

    companion object {
        @JvmStatic
        @BeforeAll
        fun connect() = TestHelper.connectToTestDb()
    }

    /**
     * GIVEN every column whose Kotlin type is a [DbToken] enum
     * WHEN its spellings are compared with the values MySQL accepts
     * THEN the two sets are identical, in both directions
     */
    @Test
    fun `every enum column matches the values its database column accepts`() {
        val columns = tokenColumns()
        assertTrue(columns.size >= 25, "expected the schema's enum columns, found ${columns.size}")

        val problems = columns.flatMap { (table, column, tokens) ->
            val accepted = acceptedValues(table, column)
                ?: return@flatMap listOf("$table.$column is declared in Kotlin but absent from the database")
            buildList {
                (tokens - accepted).takeIf { it.isNotEmpty() }
                    ?.let { add("$table.$column would write $it, which the column rejects") }
                (accepted - tokens).takeIf { it.isNotEmpty() }
                    ?.let { add("$table.$column can hold $it, which the enum cannot read") }
            }
        }

        assertTrue(
            problems.isEmpty(),
            "enum columns out of step with the schema:\n" + problems.joinToString("\n")
        )
    }

    /**
     * Every `Column<T>` on the table objects where `T` is a [DbToken] enum, as
     * `(table, column, the spellings that enum can produce)`.
     *
     * The enum comes from the property's declared generic type: the column type itself keeps only
     * the two conversion lambdas, so it cannot say which enum it converts.
     */
    private fun tokenColumns(): List<Triple<String, String, Set<String>>> =
        tableObjects().flatMap { table ->
            table::class.java.declaredFields.mapNotNull { field ->
                field.isAccessible = true
                val column = runCatching { field.get(table) }.getOrNull() as? Column<*>
                    ?: return@mapNotNull null
                val enum = Regex("""Column<([^>]+)>""").find(field.genericType.toString())
                    ?.groupValues?.get(1)
                    ?.let { runCatching { Class.forName(it) }.getOrNull() }
                    ?.takeIf { DbToken::class.java.isAssignableFrom(it) }
                    ?: return@mapNotNull null
                Triple(
                    table.tableName,
                    column.name,
                    enum.enumConstants.map { (it as DbToken).dbValue }.toSet()
                )
            }
        }

    /**
     * Every Exposed table object under `com.wini.owle.entity`.
     *
     * Found on the classpath rather than listed, so a table added later is checked without anyone
     * remembering to add it here - which is the same reason the columns are read from the schema.
     */
    private fun tableObjects(): List<Table> {
        val marker = com.wini.owle.entity.workflows.Workflows::class.java
        val root = java.io.File(marker.protectionDomain.codeSource.location.toURI())
            .resolve("com/wini/owle/entity")
        return root.walkTopDown()
            .filter { it.isFile && it.extension == "class" }
            .mapNotNull { file ->
                val name = file.relativeTo(root).path
                    .removeSuffix(".class").replace(java.io.File.separatorChar, '.')
                val cls = runCatching { Class.forName("com.wini.owle.entity.$name") }.getOrNull()
                cls?.takeIf { Table::class.java.isAssignableFrom(it) }
                    ?.let { runCatching { it.getDeclaredField("INSTANCE").get(null) as Table }.getOrNull() }
            }
            .distinct()
            .toList()
    }

    /** What MySQL will accept in the column, or null if there is no such column. */
    private fun acceptedValues(table: String, column: String): Set<String>? = transaction {
        var type: String? = null
        exec(
            """
            SELECT COLUMN_TYPE FROM information_schema.COLUMNS
            WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '$table' AND COLUMN_NAME = '$column'
            """.trimIndent()
        ) { rs -> if (rs.next()) type = rs.getString(1) }

        type?.let { t ->
            Regex("'((?:[^']|'')*)'").findAll(t)
                .map { it.groupValues[1].replace("''", "'") }
                .toSet()
        }
    }
}
