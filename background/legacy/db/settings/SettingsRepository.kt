package com.wini.owle.air.db.settings

import arrow.core.Either
import com.wini.owle.air.db.repository.LoRepository
import com.squareup.sqldelight.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.sql.SQLException


class SettingsRepository(
    val queries: SettingsEntityQueries
) {

    fun selectSettings() = queries.selectSettings()

    fun update(item: SettingsEntity): Flow<Either<Throwable, Unit>> = try {
        Either.Right(
            queries.updateSettings(
                item.autoRun,
                item.fireIntensity,
                item.textScale,
                item.closeBtnMinimizes
            )
        )
    } catch (e: SQLException) {
        Either.Left(e)
    }.run {
        flowOf(this)
    }

}