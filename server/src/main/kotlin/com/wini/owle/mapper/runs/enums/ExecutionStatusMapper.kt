package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.ExecutionStatusDto
import com.wini.owle.entity.runs.enums.ExecutionStatus

class ExecutionStatusMapper {

    fun toEntity(dto: ExecutionStatusDto): ExecutionStatus {
        return when (dto) {
            ExecutionStatusDto.Running -> ExecutionStatus.RUNNING
            ExecutionStatusDto.Completed -> ExecutionStatus.COMPLETED
            ExecutionStatusDto.Failed -> ExecutionStatus.FAILED
            ExecutionStatusDto.Skipped -> ExecutionStatus.SKIPPED
            ExecutionStatusDto.Cancelled -> ExecutionStatus.CANCELLED
        }
    }

    fun toDto(entity: ExecutionStatus): ExecutionStatusDto {
        return when (entity) {
            ExecutionStatus.RUNNING -> ExecutionStatusDto.Running
            ExecutionStatus.COMPLETED -> ExecutionStatusDto.Completed
            ExecutionStatus.FAILED -> ExecutionStatusDto.Failed
            ExecutionStatus.CANCELLED -> ExecutionStatusDto.Cancelled
            ExecutionStatus.SKIPPED -> ExecutionStatusDto.Skipped
        }
    }
}