package com.wini.owle.air.domain.model

import com.wini.owle.air.inlines.IsEnabled

data class LoItemBreakdown(
    val countTotal: Int,
    val countIsEnabled: Int,
    val countHorizontalDisabled: Int,
    val doesGroupExist: Boolean,
    val isGroupDisabled: Boolean
) {

    val countDisabledExplicit: Int
        get() = countTotal - countIsEnabled

}