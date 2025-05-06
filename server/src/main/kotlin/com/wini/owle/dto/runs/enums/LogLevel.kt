package com.wini.owle.dto.runs.enums

import kotlinx.serialization.Serializable


@Serializable
enum class LogLevel {

    /** Fine-grained debug information */
    DEBUG,

    /** Standard output useful for users */
    INFO,

    /** Unexpected situation that is recoverable */
    WARNING,

    /** Operational error or failure */
    ERROR,

    /** Irrecoverable failure or crash */
    CRITICAL

}