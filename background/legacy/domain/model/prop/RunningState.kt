package com.wini.owle.air.domain.model.prop

import com.wini.owle.air.db.root.RunningState as EntityType

sealed class RunningState(val key: Int) {

    object Stopped: RunningState(key = 0)

    object Running: RunningState(key = 1)

    companion object {
        fun RunningState.toEntity(): EntityType = when (this) {
            is Stopped -> EntityType.Stopped
            is Running -> EntityType.Running
        }

        fun fromEntity(runningState: EntityType): RunningState = when(runningState) {
            is EntityType.Stopped -> Stopped
            is EntityType.Running -> Running
        }

        fun fromServerStateKey(key: Int) : RunningState? = when (key) {
            Stopped.key -> Stopped
            Running.key -> Running
            else -> null // transitioning
        }
    }
}