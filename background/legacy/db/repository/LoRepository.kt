package com.wini.owle.air.db.repository

import app.cash.sqldelight.Transacter
import arrow.core.Either
import kotlinx.coroutines.flow.*


abstract class LoRepository<T: Any>(open val queries: Transacter) {

    /**
     * Defines the function that performs an 'insert' operation of the generic entity data type of this
     * repository. As every data type has its unique set of fields, and accordingly a unique function
     * signature in the generated [Transacter] insert function.
     *
     * Every data type (except [CalibrationEntity] and [CarPropEntity], which have their own conventions)
     * configured in the database must define a 'getMaxId' 'stored procedure' function to support id auto-incrementing.
     */
    abstract val insertEntityExecution: (T) -> Unit

    /**
     * Defines the function that performs an 'update' operation of the generic entity data type of this
     * repository. As every data type has its unique set of fields, and accordingly a unique function
     * signature in the generated [Transacter] update function.
     */
    abstract val updateEntityExecution: (T) -> Unit

    /**
     * Defines the function that performs a 'delete' operation of the generic entity data type of this
     * repository. As every data type has its unique set of fields, and accordingly a unique function
     * signature in the generated [Transacter] delete function.
     */
    abstract val deleteEntityExecution: (T) -> Unit

    /**
     * Performs an 'insert' of the repository's associated data type using the overridden execution function.
     * @param entity the database entity being inserted.
     * @return A flow represented by an [Either] representing one of two types of result -- a [Throwable]
     * of caught exception thrown during the attempted insert, or a success result with nothing but a Unit
     * to signify it.
     */
    suspend fun insert(entity: T): Flow<Either<Throwable?, Unit>> =
        flowOf(entity.runCatching(insertEntityExecution)).mapResult()

    /**
     * Performs an 'update' of the repository's associated data type using the overridden execution function.
     * @param entity the database entity being inserted.
     * @return A flow represented by an [Either] representing one of two types of result -- a [Throwable]
     * of caught exception thrown during the attempted update, or a success result with nothing but a Unit
     * to signify it.
     */

    suspend fun update(entity: T): Flow<Either<Throwable?, Unit>> =
        flowOf(entity.runCatching(updateEntityExecution)).mapResult()

    /**
     * Performs a 'delete' of the repository's associated data type using the overridden execution function.
     * @param entity the database entity being inserted. While the actual SQL operation is performed using
     * the entity's id, it is unknown what data type is underlying the table's primary key. As such, the
     * execution function is expected to provide the [Transacter] function the primary key.
     * @return A flow represented by an [Either] representing one of two types of result -- a [Throwable]
     * of caught exception thrown during the attempted insert, or a success result with nothing but a Unit
     * to signify it.
     */

    suspend fun delete(entity: T): Flow<Either<Throwable?, Unit>> =
        flowOf(entity.runCatching(deleteEntityExecution)).mapResult()

    /**
     * Maps the result of this repository's CRUD operations such that any [Throwable] caught in the operation's
     * 'runCatching' are mapped to the [Either] error side (left, by convention) and successful operations
     * are returned as a Unit in the success side. The alternative to this approach would be a sealed class
     * with multiple result subclasses. Using the arrow.io library was mostly chosen to investigate gate its viability.
     * If you think both approaches make no sense, report to Steve for a lecture about your outmoded OO-paradigm dogmatism.
     * If you're curious, next time I'll go back to result subclasses for necessarily long reasons.
     */
    fun Flow<Result<Unit>>.mapResult(): Flow<Either<Throwable?, Unit>> = map {
        if (it.isFailure) Either.Left(it.exceptionOrNull()) else Either.Right(Unit)
    }

}