package com.wini.owle.air.db.settings

import com.wini.owle.air.db.InlineAdapters

object SettingsDbAdapter {

    val instance = SettingsEntity.Adapter(
        autoRunAdapter = InlineAdapters.Bool.autoRun,
        fireIntensityAdapter = InlineAdapters.Long.fireIntensity,
        textScaleAdapter = InlineAdapters.Long.textScale,
        closeBtnMinimizesAdapter = InlineAdapters.Bool.closeBtnMinimizes
    )
}