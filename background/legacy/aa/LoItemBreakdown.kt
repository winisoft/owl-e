package com.wini.owle.air.domain.aa

data class LoItemBreakdown(
    val countTotal: Int,
    val countBoolean: Int,
    val countHorizontalDisabled: Int,
    val doesGroupExist: Boolean,
    val isGroupDisabled: Boolean
) {

    val countDisabledExplicit: Int
        get() = countTotal - countBoolean

}