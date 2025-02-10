package com.wini.owle.air.db.backoffice.endpoint

import com.wini.owle.air.db.endpoint.EndpointEntity
import com.wini.owle.air.db.endpoint.EndpointEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.EndpointId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class EndpointRepository(
    override val queries: EndpointEntityQueries
): com.wini.owle.air.db.repository.LoRepository<EndpointEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<EndpointEntity>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: EndpointId): Flow<app.cash.sqldelight.Query<EndpointEntity>> = flowOf(queries.selectById(id))

    fun getAll(): Flow<app.cash.sqldelight.Query<EndpointEntity>> = flowOf(queries.selectAll())

    fun selectAll(): Flow<List<EndpointEntity>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: EndpointId): Flow<EndpointEntity?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (EndpointEntity) -> Unit = {
        it.run { queries.insert(id, apiId, path, name) }
    }

    override val updateEntityExecution: (EndpointEntity) -> Unit = {
        it.run { queries.update(apiId, path, name, id) }
    }

    override val deleteEntityExecution: (EndpointEntity) -> Unit = {
        queries.delete(it.id)
    }

}