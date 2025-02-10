package com.wini.owle.air.ktor

sealed class ServerState(val value: Int) {

    object Stopped: ServerState(-1)

    object Transitioning: ServerState(0)

    object Running: ServerState(1)

}