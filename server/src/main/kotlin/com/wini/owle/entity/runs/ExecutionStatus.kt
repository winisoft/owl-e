package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.ExecutionStatusDto

enum class ExecutionStatus {
    RUNNING, COMPLETED, FAILED, SKIPPED, CANCELLED;

    companion object {

        fun fromDto(dto: ExecutionStatusDto): ExecutionStatus {
            return when (dto) {
                ExecutionStatusDto.Running -> RUNNING
                ExecutionStatusDto.Completed -> COMPLETED
                ExecutionStatusDto.Failed -> FAILED
                ExecutionStatusDto.Skipped -> SKIPPED
                ExecutionStatusDto.Cancelled -> CANCELLED
            }
        }

        fun ExecutionStatus.toDto(): ExecutionStatusDto {
            return when (this) {
                RUNNING -> ExecutionStatusDto.Running
                COMPLETED -> ExecutionStatusDto.Completed
                FAILED -> ExecutionStatusDto.Failed
                CANCELLED -> ExecutionStatusDto.Cancelled
                SKIPPED -> ExecutionStatusDto.Skipped
            }
        }
    }
}