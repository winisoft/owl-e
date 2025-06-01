package com.wini.owle

import org.jetbrains.exposed.v1.jdbc.transactions.transaction



//object ExposedTestUtils {
//
//    /**
//     * Sets up a mock transaction environment for Exposed in unit tests.
//     * This allows transaction blocks to execute without a real database connection.
//     */
//    fun setupMockTransactions() {
//        mockkStatic("org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManagerKt")
//        every { transaction(any(), any<Transaction.() -> Any>()) } answers {
//            @Suppress("UNCHECKED_CAST")
//            with(mockk<Transaction>()) {
//                (args[1] as Transaction.() -> Any).invoke(this)
//            }
//        }
//    }
//
//}