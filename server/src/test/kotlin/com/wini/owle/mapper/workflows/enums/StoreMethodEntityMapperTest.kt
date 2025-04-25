package com.wini.owle.mapper.workflows.enums

import com.wini.owle.dto.workflows.enums.StoreMethod
import com.wini.owle.entity.workflows.enums.StoreMethodEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class StoreMethodEntityMapperTest {

    private val mapper = StoreMethodMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(StoreMethodEntity.TEXT, mapper.toEntity(StoreMethod.Text))
        assertEquals(StoreMethodEntity.BLOB, mapper.toEntity(StoreMethod.Blob))
        assertEquals(StoreMethodEntity.FILE, mapper.toEntity(StoreMethod.File))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(StoreMethod.Text, mapper.toDto(StoreMethodEntity.TEXT))
        assertEquals(StoreMethod.Blob, mapper.toDto(StoreMethodEntity.BLOB))
        assertEquals(StoreMethod.File, mapper.toDto(StoreMethodEntity.FILE))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            StoreMethod.Text,
            StoreMethod.Blob,
            StoreMethod.File
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = StoreMethodEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}