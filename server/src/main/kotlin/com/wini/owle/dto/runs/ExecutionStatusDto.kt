package com.wini.owle.dto.runs

import kotlinx.serialization.Serializable

@Serializable
sealed class ExecutionStatusDto(val id: String) {

    @Serializable
    data object Running : ExecutionStatusDto("RUNNING")

    @Serializable
    data object Completed : ExecutionStatusDto("COMPLETED")

    @Serializable
    data object Failed : ExecutionStatusDto("FAILED")

    @Serializable
    data object Skipped : ExecutionStatusDto("SKIPPED")

    @Serializable
    data object Cancelled : ExecutionStatusDto("CANCELLED")
}

