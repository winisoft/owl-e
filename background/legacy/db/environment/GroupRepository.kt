package com.wini.owle.air.db.environment

import com.wini.owle.air.db.group.GroupEntity
import com.wini.owle.air.db.group.GroupEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.GroupId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class GroupRepository(
    override val queries: GroupEntityQueries
): com.wini.owle.air.db.repository.LoRepository<GroupEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<GroupEntity>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: GroupId): Flow<app.cash.sqldelight.Query<GroupEntity>> = flowOf(queries.selectById(id))

    fun selectAll(): Flow<List<GroupEntity>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: GroupId): Flow<GroupEntity?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (GroupEntity) -> Unit = {
        it.run { queries.insert(getNextId(), name) }
    }

    override val updateEntityExecution: (GroupEntity) -> Unit = {
        it.run { queries.update(name, id) }
    }

    override val deleteEntityExecution: (GroupEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

    private fun getNextId(): GroupId = queries.getMaxId().executeAsOne().run { GroupId(value + 1) }

//    val selectWhereEnvIdExecution: (Transacter, LoId) -> Query<SelectWhereEnvId>
//        get() = { _, idParam: LoId -> queries.selectWhereEnvId(idParam) }

}