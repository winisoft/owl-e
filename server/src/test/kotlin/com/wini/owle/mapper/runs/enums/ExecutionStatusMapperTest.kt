package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.ExecutionStatusDto
import com.wini.owle.entity.runs.enums.ExecutionStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

open class ExecutionStatusMapperTest {

    private val mapper = ExecutionStatusMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ExecutionStatus.RUNNING, mapper.toEntity(ExecutionStatusDto.Running))
        assertEquals(ExecutionStatus.COMPLETED, mapper.toEntity(ExecutionStatusDto.Completed))
        assertEquals(ExecutionStatus.FAILED, mapper.toEntity(ExecutionStatusDto.Failed))
        assertEquals(ExecutionStatus.SKIPPED, mapper.toEntity(ExecutionStatusDto.Skipped))
        assertEquals(ExecutionStatus.CANCELLED, mapper.toEntity(ExecutionStatusDto.Cancelled))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ExecutionStatusDto.Running, mapper.toDto(ExecutionStatus.RUNNING))
        assertEquals(ExecutionStatusDto.Completed, mapper.toDto(ExecutionStatus.COMPLETED))
        assertEquals(ExecutionStatusDto.Failed, mapper.toDto(ExecutionStatus.FAILED))
        assertEquals(ExecutionStatusDto.Skipped, mapper.toDto(ExecutionStatus.SKIPPED))
        assertEquals(ExecutionStatusDto.Cancelled, mapper.toDto(ExecutionStatus.CANCELLED))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            ExecutionStatusDto.Running,
            ExecutionStatusDto.Completed,
            ExecutionStatusDto.Failed,
            ExecutionStatusDto.Skipped,
            ExecutionStatusDto.Cancelled,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = ExecutionStatus.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}