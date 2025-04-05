package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.StoreMethodDto
import com.wini.owle.entity.workflows.enums.StoreMethod
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class StoreMethodMapperTest {

    private val mapper = StoreMethodMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(StoreMethod.TEXT, mapper.toEntity(StoreMethodDto.Text))
        assertEquals(StoreMethod.BLOB, mapper.toEntity(StoreMethodDto.Blob))
        assertEquals(StoreMethod.FILE, mapper.toEntity(StoreMethodDto.File))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(StoreMethodDto.Text, mapper.toDto(StoreMethod.TEXT))
        assertEquals(StoreMethodDto.Blob, mapper.toDto(StoreMethod.BLOB))
        assertEquals(StoreMethodDto.File, mapper.toDto(StoreMethod.FILE))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            StoreMethodDto.Text,
            StoreMethodDto.Blob,
            StoreMethodDto.File
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = StoreMethod.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}