package com.wini.owle.air.db.library.cal

import com.wini.owle.air.db.cal.CalibrationEntity
import com.wini.owle.air.db.cal.CalibrationEntityQueries
import com.wini.owle.air.db.repository.LoRepository
import com.wini.owle.air.inlines.CalibrationId
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class CalibrationRepository(
    override val queries: CalibrationEntityQueries
): LoRepository<CalibrationEntity>(queries) {

    fun selectAllQuery(): Flow<app.cash.sqldelight.Query<CalibrationEntity>> = flowOf(queries.selectAll())

    fun selectByIdQuery(id: CalibrationId): Flow<app.cash.sqldelight.Query<CalibrationEntity>> = flowOf(queries.selectById(id))

    override val insertEntityExecution: (CalibrationEntity) -> Unit = {
        it.run {
            queries.insert(calId, overridden, calDefFileName, calDefFileVersion, calName, calVersion,
                calType, calValue, min, max, arrayLen, enumId)
        }
    }

    override val updateEntityExecution: (CalibrationEntity) -> Unit = {
        it.run {
            queries.insert(calId, overridden, calDefFileName, calDefFileVersion, calName,
                calVersion, calType, calValue, min, max, arrayLen, enumId)
        }
    }

    override val deleteEntityExecution: (CalibrationEntity) -> Unit = {
        it.run {
            queries.delete(it.calId)
        }
    }

    fun selectAll(): Flow<List<CalibrationEntity>> = selectAllQuery().map {
        it.executeAsList()
    }

    fun selectById(id: CalibrationId): Flow<CalibrationEntity?> = selectByIdQuery(id).map {
        it.executeAsOneOrNull()
    }

}