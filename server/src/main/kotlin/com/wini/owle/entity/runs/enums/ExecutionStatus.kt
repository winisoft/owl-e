package com.wini.owle.entity.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionStatus {
    READY,
    RUNNING,
    COMPLETED,
    ERRORED,
    FAILED,
    CANCELLED
}

