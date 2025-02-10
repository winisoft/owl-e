package com.wini.owle.air.db.environment

import com.wini.owle.air.db.group.*
import com.wini.owle.air.db.group.SelectAll
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.EnvironmentGroupId
import com.wini.owle.air.inlines.EnvironmentId
import com.wini.owle.air.inlines.GroupId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class EnvironmentGroupRepository(
    override val queries: EnvironmentGroupEntityQueries
): com.wini.owle.air.db.repository.LoRepository<EnvironmentGroupEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<SelectAll>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: GroupId, envId: EnvironmentId): Flow<app.cash.sqldelight.Query<SelectByCompoundId>> = flowOf(queries.selectByCompoundId(id, envId))

    fun selectAll(): Flow<List<SelectAll>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: GroupId, envId: EnvironmentId): Flow<SelectByCompoundId?> = selectByIdQuery(id, envId).map {
        it.executeAsOneOrNull()
    }

    override val insertEntityExecution: (EnvironmentGroupEntity) -> Unit = {
        it.run {
            queries.insert(getNextGroupId(environmentId), environmentId, isEnabled, isExpanded)
        }
    }

    override val updateEntityExecution: (EnvironmentGroupEntity) -> Unit = {
        it.run { queries.update(isEnabled, isExpanded, groupId, environmentId) }
    }

    override val deleteEntityExecution: (EnvironmentGroupEntity) -> Unit = {
        it.run { queries.delete(groupId, environmentId) }
    }

    fun selectIsEnabled(envId: EnvironmentId): Flow<app.cash.sqldelight.Query<SelectGroupIdIsEnabledWhereEnvId>> = flowOf(queries.selectGroupIdIsEnabledWhereEnvId(envId))

    private fun getNextGroupId(envId: EnvironmentId): GroupId = queries.getMaxIdForEnvironment(envId).executeAsOne()

}