package com.wini.owle.air.db.library.prefs

import com.wini.owle.air.db.prefs.SharedPrefEntity
import com.wini.owle.air.db.prefs.SharedPrefEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.SharedPrefId
import com.squareup.sqldelight.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf



class SharedPrefRepository(
    override val queries: SharedPrefEntityQueries
): LoRepository<SharedPrefEntity>(queries) {

    fun all(): Flow<List<SharedPrefEntity>> = queries.selectAll().run {
        flowOf(this.executeAsList())
    }

    fun selectAllQuery(): Flow<Query<SharedPrefEntity>> = queries.selectAll().run { flowOf() }

    fun getNextId(): SharedPrefId = queries.getMaxId().executeAsOne().run { SharedPrefId(value + 1) }

    override val insertEntityExecution: (SharedPrefEntity) -> Unit = {
        it.run { queries.insert(getNextId(), sharedPrefRepoId, key, defaultValue, description, dataType) }
    }

    override val updateEntityExecution: (SharedPrefEntity) -> Unit = {
        it.run { queries.update(sharedPrefRepoId, key, defaultValue, description, dataType, id) }
    }

    override val deleteEntityExecution: (SharedPrefEntity) -> Unit = {
        it.run { queries.delete(id) }
    }

}