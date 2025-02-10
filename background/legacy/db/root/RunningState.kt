package com.wini.owle.air.db.root

sealed class RunningState(val key: Int) {

    data object Stopped: RunningState(0)

    data object Running: RunningState(1)

}