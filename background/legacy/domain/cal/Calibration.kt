package com.wini.owle.air.domain.cal


import com.wini.owle.air.inlines.*

data class Calibration(
    val calId: CalibrationId,
    val overridden: CalIsOverride,
    val calDefFileName: CalDefFileName?,
    val calDefFileVersion: CalDefFileVersion?,
    val calName: CalName,
    val calVersion: CalVersion?,
    val calType: CalType,
    val calValue: CalValue?,
    val calMin: CalMin?,
    val calMax: CalMax?,
    val arrayLen: CalArrayLen?,
    val encoding: CalEncoding?,
    val enumId: CalEnumId?
)