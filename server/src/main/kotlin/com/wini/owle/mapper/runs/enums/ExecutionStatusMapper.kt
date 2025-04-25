package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.entity.runs.enums.ExecutionStatusEntity

class ExecutionStatusMapper {

    fun toEntity(dto: ExecutionStatus): ExecutionStatusEntity {
        return when (dto) {
            ExecutionStatus.RUNNING -> ExecutionStatusEntity.RUNNING
            ExecutionStatus.COMPLETED -> ExecutionStatusEntity.COMPLETED
            ExecutionStatus.FAILED -> ExecutionStatusEntity.FAILED
            ExecutionStatus.SKIPPED -> ExecutionStatusEntity.SKIPPED
            ExecutionStatus.CANCELLED -> ExecutionStatusEntity.CANCELLED
        }
    }

    fun toDto(entity: ExecutionStatusEntity): ExecutionStatus {
        return when (entity) {
            ExecutionStatusEntity.RUNNING -> ExecutionStatus.RUNNING
            ExecutionStatusEntity.COMPLETED -> ExecutionStatus.COMPLETED
            ExecutionStatusEntity.FAILED -> ExecutionStatus.FAILED
            ExecutionStatusEntity.CANCELLED -> ExecutionStatus.CANCELLED
            ExecutionStatusEntity.SKIPPED -> ExecutionStatus.SKIPPED
        }
    }
}