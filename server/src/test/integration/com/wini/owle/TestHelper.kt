package com.wini.owle

import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


/**
 * The single place the integration tests reach the database.
 *
 * This used to carry an `ImplicatedTables` object: roughly fifty hand-written lists naming, per
 * type under test, the tables that had to be cleared to get a clean environment. It was copied
 * into three other files besides this one, and every rename to the schema silently invalidated
 * four copies at once — `Phases`, `ToolParams` and `InquisitionArgs` were still listed long after
 * the tables were dropped.
 *
 * It is gone. [resetAll] asks the database which tables exist and empties all of them, so there
 * is nothing to keep in sync. The lists existed to save time by skipping irrelevant tables, but
 * `owle_test` holds only what a test just put there, and truncating an empty InnoDB table is a
 * metadata operation — the whole schema clears in the time the old approach spent on one
 * `SchemaUtils.drop`/`create` round trip.
 */
object TestHelper {

    private const val URL = "jdbc:mysql://localhost:3306/owle_test"
    private const val DRIVER = "com.mysql.cj.jdbc.Driver"
    private const val USER = "test_runner"
    private const val PASSWORD = "b0d!ed_forth"

    @Volatile
    private var connected = false

    /** Idempotent: repeated calls from per-class setup reuse the first connection. */
    @Synchronized
    fun connectToTestDb() {
        if (connected) return
        Database.connect(url = URL, driver = DRIVER, user = USER, password = PASSWORD)
        connected = true
    }

    /** Table names cached after the first lookup; the schema does not change during a run. */
    private var tableNames: List<String>? = null

    private fun loadTableNames(): List<String> = tableNames ?: transaction {
        val names = mutableListOf<String>()
        exec(
            """
            SELECT table_name FROM information_schema.tables
            WHERE table_schema = DATABASE() AND table_type = 'BASE TABLE'
            """.trimIndent()
        ) { rs ->
            while (rs.next()) names += rs.getString(1)
        }
        check(names.isNotEmpty()) {
            "owle_test reports no tables. Is the schema loaded? It should mirror owle_dev exactly."
        }
        names.toList().also { tableNames = it }
    }

    /**
     * Empties every table. Foreign key checks are suspended for the duration so the order the
     * tables come back in cannot matter, and `TRUNCATE` is used rather than `DELETE` so
     * auto-increment counters restart — a test that asserts on a returned id gets the same id
     * whether it runs alone or after the rest of the suite.
     */
    fun resetAll() {
        connectToTestDb()
        transaction {
            exec("SET FOREIGN_KEY_CHECKS = 0")
            try {
                loadTableNames().forEach { exec("TRUNCATE TABLE `$it`") }
            } finally {
                exec("SET FOREIGN_KEY_CHECKS = 1")
            }
        }
    }

    /**
     * Empties only the named tables, for a test that wants to leave the rest of the schema alone.
     * Prefer [resetAll] unless you have a reason — a test that depends on rows it did not create
     * is a test that passes for reasons nobody wrote down.
     */
    fun reset(vararg tables: String) {
        connectToTestDb()
        transaction {
            exec("SET FOREIGN_KEY_CHECKS = 0")
            try {
                tables.forEach { exec("TRUNCATE TABLE `$it`") }
            } finally {
                exec("SET FOREIGN_KEY_CHECKS = 1")
            }
        }
    }
}
