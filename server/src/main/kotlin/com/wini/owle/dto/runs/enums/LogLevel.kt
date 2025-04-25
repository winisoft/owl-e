package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable

@Serializable
enum class LogLevel {

    DEBUG,

    INFO,

    WARNING,

    ERROR,

    CRITICAL

}