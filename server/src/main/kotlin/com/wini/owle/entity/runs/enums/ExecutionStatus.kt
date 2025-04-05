package com.wini.owle.entity.runs.enums

import com.wini.owle.dto.runs.ExecutionStatusDto

enum class ExecutionStatus {
    RUNNING, COMPLETED, FAILED, SKIPPED, CANCELLED;
}