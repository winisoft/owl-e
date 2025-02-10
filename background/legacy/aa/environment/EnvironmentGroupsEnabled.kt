package com.wini.owle.air.domain.aa.environment

data class EnvironmentGroupsEnabled(
    val isApiOverrideGroupEnabled: Boolean?,
    val isCalOverrideGroupEnabled: Boolean?,
    val isCarOverrideGroupEnabled: Boolean?,
    val isSystemPropOverrideGroupEnabled: Boolean?,
    val isSharedPrefOverrideGroupEnabled: Boolean?
)