package com.wini.owle.air.domain.aa.environment

import com.wini.owle.air.domain.aa.LoItemBreakdown


data class EnvironmentBreakdown(
    val apiOverrideBreakdown: LoItemBreakdown,
    val calOverrideBreakdown: LoItemBreakdown,
    val carOverrideBreakdown: LoItemBreakdown,
    val systemPropOverrideBreakdown: LoItemBreakdown,
    val sharedPrefOverrideBreakdown: LoItemBreakdown
)