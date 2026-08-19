package com.wini.owle.dto.runs.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionLogLevel(override val dbValue: String) : DbToken {

    TRACE("TRACE"),

    DEBUG("DEBUG"),

    INFO("INFO"),

    WARN("WARN"),

    ERROR("ERROR")

}
