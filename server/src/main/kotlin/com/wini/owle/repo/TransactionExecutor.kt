package com.wini.owle.repo

import org.jetbrains.exposed.v1.jdbc.transactions.transaction


/**
 * The seam between a repository and Exposed's transaction machinery.
 *
 * `transaction { }` is a top-level function that reaches for a registered `Database` the moment
 * it is entered. A repository that calls it directly cannot be exercised without a live
 * connection — not because the repository needs one, but because the call it is wrapped in does.
 * That is what made the repository unit tests impossible to write: every assertion about "does
 * this call `entityClass.all()`" first had to satisfy a database that had nothing to do with the
 * question.
 *
 * Routing through this interface moves the decision to the constructor. Production gets
 * [ExposedTransactionExecutor] and behaves exactly as before; a test supplies [PassThrough] and
 * the repository runs its block inline against a mocked `EntityClass`.
 */
// Not a `fun interface`: a functional interface cannot declare a generic abstract method.
interface TransactionExecutor {

    /** Runs [block] inside whatever transactional context this executor represents. */
    fun <T> run(block: () -> T): T

    companion object {

        /**
         * Runs the block with no transaction at all.
         *
         * Correct only where the block does no real database work — a unit test against a mocked
         * `EntityClass`. Using it in production would silently drop atomicity.
         */
        val PassThrough: TransactionExecutor = object : TransactionExecutor {
            override fun <T> run(block: () -> T): T = block()
        }
    }
}


/** The real thing: every call is wrapped in an Exposed transaction, as before. */
object ExposedTransactionExecutor : TransactionExecutor {
    override fun <T> run(block: () -> T): T = transaction { block() }
}
