package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionLogLevel {

    TRACE,

    DEBUG,

    INFO,

    WARN,

    ERROR

}
