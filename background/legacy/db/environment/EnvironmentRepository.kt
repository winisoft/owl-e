package com.wini.owle.air.db.environment


import arrow.core.Either
import com.wini.owle.air.db.error.NoEntityFoundException
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.db.sysprop.SelectSystemPropOverrideBreakdown
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class EnvironmentRepository(
    override val queries: EnvironmentEntityQueries
): com.wini.owle.air.db.repository.LoRepository<EnvironmentEntity>(queries) {

    override val insertEntityExecution: (EnvironmentEntity) -> Unit = {
        queries.insert(it.id, it.name, it.description, it.isEnabled, it.isExpanded, it.isHorizontalDisabled)
    }

    /**
     * Defines the function that performs an 'update' operation of the generic entity data type of this
     * repository. As every data type has its unique set of fields, and accordingly a unique function
     * signature in the generated [Transacter] update function.
     */
    override val updateEntityExecution: (EnvironmentEntity) -> Unit = {
        queries.update(it.name, it.description, it.isEnabled, it.isExpanded, it.isHorizontalDisabled, it.id)
    }

    /**
     * Defines the function that performs a 'delete' operation of the generic entity data type of this
     * repository. As every data type has its unique set of fields, and accordingly a unique function
     * signature in the generated [Transacter] delete function.
     */
    override val deleteEntityExecution: (EnvironmentEntity) -> Unit = {
        queries.delete(it.id)
    }

    fun updateSetIsEnabledTrue(
        id: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateEnvironmentSetIsEnabledTrue(id, id) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

    fun selectById(id: EnvironmentId): Flow<Either<Throwable, SelectById>> = flowOf(queries.selectById(id).executeAsOneOrNull().let {
            if (it == null)
                Either.Left(com.wini.owle.air.db.error.NoEntityFoundException().apply { fillInStackTrace() } as Throwable)
            else
                Either.Right(it)
        })

    fun selectIdOfEnabled(): Flow<EnvironmentId?> = flowOf(queries.selectIdWhereEnabled().executeAsOneOrNull())

}
