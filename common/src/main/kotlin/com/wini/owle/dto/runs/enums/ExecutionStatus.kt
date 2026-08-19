package com.wini.owle.dto.runs.enums

import com.wini.owle.dto.DbToken
import kotlinx.serialization.Serializable

@Serializable
enum class ExecutionStatus(override val dbValue: String) : DbToken {
    READY("ready"),
    RUNNING("running"),
    COMPLETED("completed"),
    ERRORED("errored"),
    FAILED("failed"),
    CANCELLED("cancelled")
}

