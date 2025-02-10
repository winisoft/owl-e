package com.wini.owle.air.domain.settings

import arrow.core.Either
import com.wini.owle.air.db.library.sysprop.SystemPropRepository
import com.wini.owle.air.db.settings.SettingsEntity
import com.wini.owle.air.db.settings.SettingsRepository
import com.wini.owle.air.db.sysprop.SystemPropEntity
import com.wini.owle.air.domain.model.prop.DataType
import com.wini.owle.air.domain.repo.DomainAccessor
import com.wini.owle.air.domain.sysprop.SystemProp
import com.wini.owle.air.inlines.AutoRun
import com.wini.owle.air.inlines.CloseBtnMinimizes
import com.wini.owle.air.inlines.FireIntensity
import com.wini.owle.air.inlines.TextScale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


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