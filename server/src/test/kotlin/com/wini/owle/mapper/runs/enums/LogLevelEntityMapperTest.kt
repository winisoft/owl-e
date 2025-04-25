package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.entity.runs.enums.LogLevelEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class LogLevelEntityMapperTest {

    private val mapper = LogLevelMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(LogLevelEntity.DEBUG, mapper.toEntity(LogLevel.Debug))
        assertEquals(LogLevelEntity.INFO, mapper.toEntity(LogLevel.Info))
        assertEquals(LogLevelEntity.WARNING, mapper.toEntity(LogLevel.Warning))
        assertEquals(LogLevelEntity.ERROR, mapper.toEntity(LogLevel.Error))
        assertEquals(LogLevelEntity.CRITICAL, mapper.toEntity(LogLevel.Critical))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(LogLevel.Debug, mapper.toDto(LogLevelEntity.DEBUG))
        assertEquals(LogLevel.Info, mapper.toDto(LogLevelEntity.INFO))
        assertEquals(LogLevel.Warning, mapper.toDto(LogLevelEntity.WARNING))
        assertEquals(LogLevel.Error, mapper.toDto(LogLevelEntity.ERROR))
        assertEquals(LogLevel.Critical, mapper.toDto(LogLevelEntity.CRITICAL))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            LogLevel.Debug,
            LogLevel.Info,
            LogLevel.Warning,
            LogLevel.Error,
            LogLevel.Critical,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped, "Round-trip failed for DTO: $dto")
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = LogLevelEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}