package com.wini.owle.air.db.backoffice.api

import arrow.core.Either
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.ApiId
import com.wini.owle.air.inlines.ApiOverrideId
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.LoRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class ApiOverrideRepository(
    override val queries: ApiOverrideEntityQueries,
): com.wini.owle.air.db.repository.LoRepository<ApiOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map { it.executeAsList() }

    fun selectByIdQuery(id: ApiOverrideId): Flow<app.cash.sqldelight.Query<SelectById>> = flowOf(queries.selectById(id))

    fun selectById(id: ApiOverrideId): Flow<SelectById?> = selectByIdQuery(id).map { it.executeAsOneOrNull() }

    override val insertEntityExecution: (ApiOverrideEntity) -> Unit = {
        it.run { queries.insert(getNextId(), apiId, environmentId, overrideName, isEnabled, isExpanded) }
    }

    override val updateEntityExecution: (ApiOverrideEntity) -> Unit = {
        it.run { queries.update(apiId, environmentId, overrideName, isEnabled, isExpanded, id) }
    }

    override val deleteEntityExecution: (ApiOverrideEntity) -> Unit = {
        it.run { queries.delete(it.id) }
    }

    private fun getNextId(): ApiOverrideId = queries.getMaxId().executeAsOne().run { ApiOverrideId(value + 1) }

    fun countInEnvironment(id: EnvironmentId): Int = queries.countWhereEnvId(id).executeAsOne().toInt()

    fun selectEndpointOverridesWithEnabledStackWhereApiHasPrefix(
        prefix: LoRoute
    ): List<SelectEnabledEndpointOverridesWhereApiHasPrefix> = queries
        .selectEnabledEndpointOverridesWhereApiHasPrefix(prefix)
        .executeAsList()

    fun updateSetIsEnabledTrue(
        id: ApiOverrideId,
        apiId: ApiId,
        env: EnvironmentId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateApiOverrideSetIsEnabledTrue(id, id, apiId, env) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }

    fun getApiOverrideBreakdown(envId: EnvironmentId): Flow<SelectApiOverrideBreakdown> {
        return flowOf(queries.selectApiOverrideBreakdown(envId).executeAsOne())
    }
}