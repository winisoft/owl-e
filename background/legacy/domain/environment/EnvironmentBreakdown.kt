package com.wini.owle.air.domain.environment

import com.wini.owle.air.domain.model.LoItemBreakdown
import com.wini.owle.air.inlines.IsEnabled


data class EnvironmentBreakdown(
    val apiOverrideBreakdown: LoItemBreakdown,
    val calOverrideBreakdown: LoItemBreakdown,
    val carOverrideBreakdown: LoItemBreakdown,
    val systemPropOverrideBreakdown: LoItemBreakdown,
    val sharedPrefOverrideBreakdown: LoItemBreakdown
)