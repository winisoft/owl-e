package com.wini.owle.air.domain.aa

import arrow.core.Either
import com.wini.owle.air.db.settings.SettingsEntity
import com.wini.owle.air.db.settings.SettingsRepository
import com.wini.owle.air.domain.settings.Settings
import kotlinx.coroutines.flow.Flow


class SettingsAccessor(
    private val repository: SettingsRepository
) {

    fun get(): Settings = repository.selectSettings().executeAsOne().let {
        Settings(
            autoRun = it.autoRun,
            fireIntensity = it.fireIntensity,
            textScale = it.textScale,
            closeBtnMinimizes = it.closeBtnMinimizes
        )
    }

    fun update(settings: Settings): Flow<Either<Throwable?, Unit>> = repository.update(settings.mapToEntity())

    fun Settings.mapToEntity(): SettingsEntity = SettingsEntity(
        autoRun, fireIntensity, textScale, closeBtnMinimizes
    )


}