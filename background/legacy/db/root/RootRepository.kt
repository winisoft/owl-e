package com.wini.owle.air.db.root

import arrow.core.Either
import com.wini.owle.air.db.LoRootItemEntity
import com.wini.owle.air.db.LoRootItemQueries
import com.squareup.sqldelight.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.sql.SQLException

class RootRepository(
    val queries: LoRootItemQueries
) {

    fun selectWhereId0() = queries.selectWhereId0()

    fun setIsExpandedFalse() = try {
        Either.Right(queries.updateIsExpandedFalse())
    } catch (e: SQLException) {
        Either.Left(e)
    }.run {
        flowOf(this)
    }

    fun update(item: LoRootItemEntity): Flow<Either<Throwable, Unit>> = try {
        Either.Right(queries.update(item.runningState, item.isExpanded))
    } catch (e: SQLException) {
        Either.Left(e)
    }.run {
        flowOf(this)
    }

    fun updateSetRunningState(runningState: com.wini.owle.air.db.root.RunningState)  = try {
        Either.Right(queries.updateSetRunningState(runningState))
    } catch (e: SQLException) {
        Either.Left(e)
    }.run {
        flowOf(this)
    }


}