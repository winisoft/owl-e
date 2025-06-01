package com.wini.owle.dto.runs.enums

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

