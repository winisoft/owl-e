package com.wini.owle.air.db.library.sysprop

import app.cash.sqldelight.db.SqlDriver
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.db.sysprop.SystemPropEntityQueries
import com.wini.owle.air.inlines.SystemPropId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class SystemPropRepository(
    override val queries: SystemPropEntityQueries
): com.wini.owle.air.db.repository.LoRepository<SystemPropEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SystemPropEntity>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: SystemPropId): Flow<app.cash.sqldelight.Query<SystemPropEntity>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<SystemPropEntity>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun all(): Flow<List<SystemPropEntity>> = flowOf(queries.selectAll().executeAsList())

    fun selectById(id: SystemPropId): Flow<SystemPropEntity?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (SystemPropEntity) -> Unit = {
        it.run { queries.insert(getNextId(), key, dataType, description) }
    }

    override val updateEntityExecution: (SystemPropEntity) -> Unit = {
        it.run { queries.update(key, dataType, description, id) }
    }

    override val deleteEntityExecution: (SystemPropEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

    private fun getNextId(): SystemPropId = queries.getMaxId().executeAsOne().run { SystemPropId(value + 1) }
}