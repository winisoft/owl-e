package com.wini.owle.air.domain.settings

import com.wini.owle.air.inlines.AutoRun
import com.wini.owle.air.inlines.CloseBtnMinimizes
import com.wini.owle.air.inlines.FireIntensity
import com.wini.owle.air.inlines.TextScale

data class Settings(
    val autoRun: AutoRun,
    val fireIntensity: FireIntensity,
    val textScale: TextScale,
    val closeBtnMinimizes: CloseBtnMinimizes
)