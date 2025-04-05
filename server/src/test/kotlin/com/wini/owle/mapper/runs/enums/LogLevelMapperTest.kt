package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.LogLevelDto
import com.wini.owle.entity.runs.enums.LogLevel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class LogLevelMapperTest {

    private val mapper = LogLevelMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(LogLevel.DEBUG, mapper.toEntity(LogLevelDto.Debug))
        assertEquals(LogLevel.INFO, mapper.toEntity(LogLevelDto.Info))
        assertEquals(LogLevel.WARNING, mapper.toEntity(LogLevelDto.Warning))
        assertEquals(LogLevel.ERROR, mapper.toEntity(LogLevelDto.Error))
        assertEquals(LogLevel.CRITICAL, mapper.toEntity(LogLevelDto.Critical))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(LogLevelDto.Debug, mapper.toDto(LogLevel.DEBUG))
        assertEquals(LogLevelDto.Info, mapper.toDto(LogLevel.INFO))
        assertEquals(LogLevelDto.Warning, mapper.toDto(LogLevel.WARNING))
        assertEquals(LogLevelDto.Error, mapper.toDto(LogLevel.ERROR))
        assertEquals(LogLevelDto.Critical, mapper.toDto(LogLevel.CRITICAL))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            LogLevelDto.Debug,
            LogLevelDto.Info,
            LogLevelDto.Warning,
            LogLevelDto.Error,
            LogLevelDto.Critical,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped, "Round-trip failed for DTO: $dto")
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = LogLevel.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}