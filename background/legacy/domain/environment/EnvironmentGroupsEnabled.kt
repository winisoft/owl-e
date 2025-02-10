package com.wini.owle.air.domain.environment

data class EnvironmentGroupsEnabled(
    val isApiOverrideGroupEnabled: Boolean?,
    val isCalOverrideGroupEnabled: Boolean?,
    val isCarOverrideGroupEnabled: Boolean?,
    val isSystemPropOverrideGroupEnabled: Boolean?,
    val isSharedPrefOverrideGroupEnabled: Boolean?
)