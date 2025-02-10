package com.wini.owle.air.db.library.prefs

import arrow.core.Either
import com.wini.owle.air.db.api.SelectApiOverrideBreakdown
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.prefs.*
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class SharedPrefOverrideRepository(
    override val queries: SharedPrefOverrideEntityQueries
): com.wini.owle.air.db.repository.LoRepository<SharedPrefOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<Query<SelectAll>> = queries.selectAll().run { flowOf() }

    fun selectByIdQuery(id: SharedPrefOverrideId): Flow<Query<SelectById>> = queries.selectById(id).run { flowOf() }

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map { it.executeAsList() }

    fun selectById(id: SharedPrefOverrideId): Flow<SelectById?> = selectByIdQuery(id).map { it.executeAsOneOrNull() }

    override val insertEntityExecution: (SharedPrefOverrideEntity) -> Unit = {
        it.run { queries.insert(getNextId(), sharedPrefId, environmentId, overrideValue, description, isEnabled) }
    }

    override val updateEntityExecution: (SharedPrefOverrideEntity) -> Unit = {
        it.run { queries.update(sharedPrefId, environmentId, overrideValue, description, isEnabled, id) }
    }

    override val deleteEntityExecution: (SharedPrefOverrideEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

    fun getNextId(): SharedPrefOverrideId = queries.getMaxId().executeAsOne().run { SharedPrefOverrideId(value + 1) }

    fun updateSetIsEnabledTrue(
        id: SharedPrefOverrideId,
        sharedPrefId: SharedPrefId,
        envId: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateSharedPrefOverrideSetIsEnabledTrue(id, id, sharedPrefId, envId) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

    fun selectWhereEnvId(envId: EnvironmentId): Flow<List<SelectWhereEnvironmentId>> = flowOf(queries
        .selectWhereEnvironmentId(envId).executeAsList())

    fun getSharedPrefOverrideBreakdown(envId: EnvironmentId): Flow<SelectSharedPrefOverrideBreakdown> {
        return flowOf(queries.selectSharedPrefOverrideBreakdown(envId).executeAsOne())
    }
}