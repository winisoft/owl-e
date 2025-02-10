package com.wini.owle.air.db.backoffice.api

import com.wini.owle.air.db.api.ApiEntity
import com.wini.owle.air.db.api.ApiEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.ApiId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ApiRepository(
    override val queries: ApiEntityQueries
): com.wini.owle.air.db.repository.LoRepository<ApiEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<ApiEntity>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: ApiId): Flow<app.cash.sqldelight.Query<ApiEntity>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<ApiEntity>> = flowOf(queries.selectAll()).map { it.executeAsList() }

    fun selectById(id: ApiId): Flow<ApiEntity?> = flowOf(queries.selectById(id)).map { it.executeAsOneOrNull() }

    private fun getNextId(): Flow<ApiId> = flowOf(queries.getMaxId().executeAsOne().run { ApiId(value + 1) })

    override val insertEntityExecution: (ApiEntity) -> Unit = { entity ->
        getNextId().map {
            entity.run { queries.insert(it, name, loRoute, openApi) }
        }
    }

    override val updateEntityExecution: (ApiEntity) -> Unit = { entity ->
        entity.run {
            queries.update(
                name, loRoute, openApi, id
            )
        }
    }

    override val deleteEntityExecution: (ApiEntity) -> Unit = {
        queries.delete(it.id)
    }

}