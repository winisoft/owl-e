package com.wini.owle.ksp.writer

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File


/**
 * Reading composite keys out of the table sources.
 *
 * This reads Kotlin as text, because KSP exposes declarations but not initialiser expressions, so
 * `override val primaryKey = PrimaryKey(step, tag)` names columns the symbol API cannot reach. Text
 * matching is fragile in a specific way: when a pattern stops matching, the table reads as *having
 * no key columns* rather than as an error, and the only visible effect is a delegate that quietly
 * never gets generated.
 *
 * That has now happened twice in two days — once when `dbEnum<VersionOperator>("operator", 16)`
 * introduced a type-argument list the column pattern did not allow, and once when key columns moved
 * to `keyReference(...)`. Both surfaced as unresolved-reference errors in generated mappers, a long
 * way from the cause. Each declaration form the table sources actually use is pinned here.
 */
@DisplayName("Composite key reader")
class CompositeKeyReaderTest {

    @TempDir
    lateinit var dir: File

    private fun read(source: String): Map<String, CompositeKeyReader.Table> {
        val file = File(dir, "ExposedTables.kt").apply { writeText(source) }
        return CompositeKeyReader.read(listOf(file.path))
    }

    /**
     * GIVEN a composite table whose key columns are plain references
     * WHEN the sources are read
     * THEN both key columns are found, in the order the primary key declares them
     */
    @Test
    fun `reads key columns from plain references`() {
        val tables = read(
            """
            object StepTags : CompositeIdTable("tagged_steps") {
                val step = reference("step_id", Steps)
                val tag = reference("tag_id", Tags)
                override val primaryKey = PrimaryKey(step, tag)
            }
            """.trimIndent()
        )

        val table = tables.getValue("StepTags")
        assertEquals("tagged_steps", table.tableName)
        assertEquals(listOf("step", "tag"), table.keyProperties)
        assertEquals(listOf("step_id", "tag_id"), table.keyColumns.map { it.dbName })
        assertEquals(listOf("Steps", "Tags"), table.keyColumns.map { it.referencedTable })
    }

    /**
     * GIVEN key columns declared with `keyReference`, which is how they register as id columns
     * WHEN the sources are read
     * THEN they are read exactly as plain references are
     */
    @Test
    fun `reads key columns declared with keyReference`() {
        val tables = read(
            """
            object DistributionTags : OwleCompositeIdTable("tagged_distributions") {
                val distribution = keyReference("distribution", Distributions, onDelete = ReferenceOption.CASCADE)
                val tag = keyReference("tag_id", Tags, onDelete = ReferenceOption.CASCADE)
                override val primaryKey = PrimaryKey(distribution, tag)
            }
            """.trimIndent()
        )

        val table = tables.getValue("DistributionTags")
        assertEquals(listOf("distribution", "tag_id"), table.keyColumns.map { it.dbName })
        assertEquals(listOf("Distributions", "Tags"), table.keyColumns.map { it.referencedTable })
    }

    /**
     * GIVEN a reference carrying extra arguments
     * WHEN the sources are read
     * THEN the referenced table is still identified
     *
     * `onDelete = ReferenceOption.CASCADE` sits between the referenced table and the closing paren,
     * which an earlier pattern stopped at.
     */
    @Test
    fun `a reference with extra arguments still names its table`() {
        val tables = read(
            """
            object Grouped : OwleCompositeIdTable("grouped") {
                val grouper = keyReference("grouper_id", Groupers, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.NO_ACTION)
                val tag = keyReference("tag_id", Tags)
                override val primaryKey = PrimaryKey(grouper, tag)
            }
            """.trimIndent()
        )

        assertEquals("Groupers", tables.getValue("Grouped").keyColumns.first().referencedTable)
    }

    /**
     * GIVEN a key column whose declaration carries a type argument
     * WHEN the sources are read
     * THEN it is found
     *
     * `dbEnum<VersionOperator>("operator", 16)` is the form that broke this: the column pattern
     * allowed no `<...>`, so the specifier tables read as keyless and their delegates were never
     * generated.
     */
    @Test
    fun `reads a key column declared with a type argument`() {
        val tables = read(
            """
            object PythonVersionSpecifiers : OwleCompositeIdTable("python_version_specifiers") {
                val pythonVersion = keyReference("python_version_id", PythonVersions)
                val operator = dbEnum<VersionOperator>("operator", 16).entityId()
                override val primaryKey = PrimaryKey(pythonVersion, operator)
            }
            """.trimIndent()
        )

        val table = tables.getValue("PythonVersionSpecifiers")
        assertEquals(listOf("python_version_id", "operator"), table.keyColumns.map { it.dbName })
        assertNull(
            table.keyColumns.last().referencedTable,
            "an enum column references no table"
        )
    }

    /**
     * GIVEN a table body containing nested braces
     * WHEN the sources are read
     * THEN the body is matched by depth rather than to the first closing brace
     */
    @Test
    fun `a nested brace does not end the table body early`() {
        val tables = read(
            """
            object Checked : OwleCompositeIdTable("checked") {
                val left = keyReference("left_id", Lefts)
                init {
                    check("something") { left.isNotNull() }
                }
                val right = keyReference("right_id", Rights)
                override val primaryKey = PrimaryKey(left, right)
            }
            """.trimIndent()
        )

        assertEquals(listOf("left_id", "right_id"), tables.getValue("Checked").keyColumns.map { it.dbName })
    }

    /**
     * GIVEN several composite tables in one file
     * WHEN the sources are read
     * THEN each is read separately and keyed by its object name
     */
    @Test
    fun `reads every table in a file`() {
        val tables = read(
            """
            object First : OwleCompositeIdTable("first") {
                val a = keyReference("a_id", As)
                val b = keyReference("b_id", Bs)
                override val primaryKey = PrimaryKey(a, b)
            }

            object Second : OwleCompositeIdTable("second") {
                val c = keyReference("c_id", Cs)
                val d = keyReference("d_id", Ds)
                override val primaryKey = PrimaryKey(c, d)
            }
            """.trimIndent()
        )

        assertEquals(setOf("First", "Second"), tables.keys)
        assertEquals(listOf("c_id", "d_id"), tables.getValue("Second").keyColumns.map { it.dbName })
    }

    /**
     * GIVEN a composite table with no primary key declaration
     * WHEN the sources are read
     * THEN it reports no key columns rather than inventing them
     *
     * The caller treats an empty key as "cannot generate a delegate for this" and says so; the one
     * thing that must not happen is a delegate built from a guess.
     */
    @Test
    fun `a table with no declared key reports none`() {
        val tables = read(
            """
            object Keyless : OwleCompositeIdTable("keyless") {
                val a = keyReference("a_id", As)
            }
            """.trimIndent()
        )

        assertTrue(tables.getValue("Keyless").keyColumns.isEmpty())
    }

    /**
     * GIVEN a path that is not a readable file
     * WHEN the sources are read
     * THEN it is skipped rather than throwing
     */
    @Test
    fun `an unreadable path is skipped`() {
        assertTrue(CompositeKeyReader.read(listOf(File(dir, "absent.kt").path)).isEmpty())
    }
}
