package com.wini.owle.air.db.library.sysprop

import app.cash.sqldelight.Query
import arrow.core.Either
import com.wini.owle.air.db.sysprop.*
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class SystemOverrideRepository(
    override val queries: SystemPropOverrideEntityQueries
): com.wini.owle.air.db.repository.LoRepository<SystemPropOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: SystemPropOverrideId): Flow<Query<SelectById>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map { it.executeAsList() }

    fun selectById(id: SystemPropOverrideId): Flow<SelectById?> = selectByIdQuery(id).map { it.executeAsOneOrNull() }

    override val insertEntityExecution: (SystemPropOverrideEntity) -> Unit = {
        it.run { queries.insert(getNextId(), systemPropId, environmentId, description, overrideValue, isEnabled) }
    }

    override val updateEntityExecution: (SystemPropOverrideEntity) -> Unit = {
        it.run { queries.update(systemPropId, environmentId, overrideValue, isEnabled, description, id) }
    }

    override val deleteEntityExecution: (SystemPropOverrideEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

    fun getNextId(): SystemPropOverrideId = queries.getMaxId().executeAsOne().run { SystemPropOverrideId(value + 1) }

    fun updateSetIsEnabledTrue(
        id: SystemPropOverrideId,
        sysPropId: SystemPropId,
        envId: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateSysPropOverrideSetIsEnabledTrue(id, id, sysPropId, envId) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

    fun selectWhereEnvironmentId(envId: EnvironmentId): Flow<List<SelectWhereEnvironmentId>> =
        flowOf(queries.selectWhereEnvironmentId(envId).executeAsList())

    fun getSystemPropOverrideBreakdown(envId: EnvironmentId): Flow<Query<SelectSystemPropOverrideBreakdown>> {
        return flowOf(queries.selectSystemPropOverrideBreakdown(envId))
    }
}