package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.ExecutionStatus
import com.wini.owle.entity.runs.enums.ExecutionStatusEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class ExecutionStatusEntityMapperTest {

    private val mapper = ExecutionStatusMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ExecutionStatusEntity.RUNNING, mapper.toEntity(ExecutionStatus.Running))
        assertEquals(ExecutionStatusEntity.COMPLETED, mapper.toEntity(ExecutionStatus.Completed))
        assertEquals(ExecutionStatusEntity.FAILED, mapper.toEntity(ExecutionStatus.Failed))
        assertEquals(ExecutionStatusEntity.SKIPPED, mapper.toEntity(ExecutionStatus.Skipped))
        assertEquals(ExecutionStatusEntity.CANCELLED, mapper.toEntity(ExecutionStatus.Cancelled))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ExecutionStatus.Running, mapper.toDto(ExecutionStatusEntity.RUNNING))
        assertEquals(ExecutionStatus.Completed, mapper.toDto(ExecutionStatusEntity.COMPLETED))
        assertEquals(ExecutionStatus.Failed, mapper.toDto(ExecutionStatusEntity.FAILED))
        assertEquals(ExecutionStatus.Skipped, mapper.toDto(ExecutionStatusEntity.SKIPPED))
        assertEquals(ExecutionStatus.Cancelled, mapper.toDto(ExecutionStatusEntity.CANCELLED))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            ExecutionStatus.Running,
            ExecutionStatus.Completed,
            ExecutionStatus.Failed,
            ExecutionStatus.Skipped,
            ExecutionStatus.Cancelled,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = ExecutionStatusEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}