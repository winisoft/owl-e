package com.wini.owle.air.db.backoffice.endpoint

import arrow.core.Either
import com.wini.owle.air.db.endpoint.EndpointOverrideEntity
import com.wini.owle.air.db.endpoint.EndpointOverrideEntityQueries
import com.wini.owle.air.db.endpoint.SelectAll
import com.wini.owle.air.db.endpoint.SelectById
import com.wini.owle.air.db.error.UnknownDbExecuteException
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.*
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class EndpointOverrideRepository(
    override val queries: EndpointOverrideEntityQueries
): com.wini.owle.air.db.repository.LoRepository<EndpointOverrideEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: EndpointOverrideId): Flow<app.cash.sqldelight.Query<SelectById>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: EndpointOverrideId): Flow<SelectById?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (EndpointOverrideEntity) -> Unit = {
        it.run { queries.insert(getNextId(), endpointId, apiOverrideId, overrideName, description, isEnabled, isExpanded) }
    }

    override val updateEntityExecution: (EndpointOverrideEntity) -> Unit = {
        it.run { queries.update(endpointId, apiOverrideId, overrideName, description, isEnabled, isExpanded, id) }
    }

    override val deleteEntityExecution: (EndpointOverrideEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

    private fun getNextId(): EndpointOverrideId = queries.getMaxId().executeAsOne().run { EndpointOverrideId(value + 1) }

    fun updateSetIsEnabledTrue(
        id: EndpointOverrideId,
        apiOverrideId: ApiOverrideId
    ): Flow<Either<Throwable?, Unit>> = runCatching { queries.updateEndpointOverrideSetIsEnabledTrue(id, id, apiOverrideId) }.run {
        exceptionOrNull()?.run {
            flowOf(Either.Left(this))
        } ?: getOrNull()?.run {
            flowOf(Either.Right(this))
        } ?: flowOf(Either.Left(com.wini.owle.air.db.error.UnknownDbExecuteException()))
    }
}