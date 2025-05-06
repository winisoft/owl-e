package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionStatus {

    RUNNING,
    COMPLETED,
    FAILED,
    SKIPPED,
    CANCELLED
    
}

