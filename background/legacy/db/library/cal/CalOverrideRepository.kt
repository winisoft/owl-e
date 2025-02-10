package com.wini.owle.air.db.library.cal

import app.cash.sqldelight.Query
import arrow.core.Either
import arrow.core.rightIfNull
import com.wini.owle.air.db.cal.*
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class CalOverrideRepository(
    override val queries: CalOverrideEntityQueries
): LoRepository<CalOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: CalOverrideId): Flow<Query<SelectById>> = flowOf(queries.selectById(id))

    fun getNextId(): CalOverrideId = queries.getMaxId().executeAsOne().run { CalOverrideId(this.value + 1) }

    fun getAllWhereEnvId(envId: EnvironmentId): Flow<List<SelectWhereEnvId>> = listOf(queries.selectWhereEnvId(envId).executeAsList()).asFlow()

    override val insertEntityExecution: (CalOverrideEntity) -> Unit = {
        it.run {
            queries.insert(getNextId(), environmentId, calId, overrideName, overrideValue, isEnabled)
        }
    }

    override val updateEntityExecution: (CalOverrideEntity) -> Unit = {
        it.run {
            queries.update(environmentId, calId, overrideName, overrideValue, isEnabled, id)
        }
    }

    override val deleteEntityExecution: (CalOverrideEntity) -> Unit = {
        it.run {
            queries.delete(it.id)
        }
    }

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: CalOverrideId): Flow<SelectById?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    val countWhereEnvIdExecution: (EnvironmentId) -> Query<Long>
        get() = { id: EnvironmentId ->
            queries.countWhereEnvId(id)
        }

    fun countWhereEnvId(id: EnvironmentId) = countWhereEnvIdExecution(id)

    fun updateSetIsEnabledTrue(
        id: CalOverrideId,
        calId: CalibrationId,
        envId: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateCalOverrideSetIsEnabledTrue(id, id, calId, envId) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

}