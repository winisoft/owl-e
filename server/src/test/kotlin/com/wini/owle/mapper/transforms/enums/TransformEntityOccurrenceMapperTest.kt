package com.wini.owle.mapper.transforms.enums

import com.wini.owle.dto.transforms.enums.TransformOccurrence
import com.wini.owle.entity.transforms.enums.TransformOccurrenceEntity
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


open class TransformEntityOccurrenceMapperTest {

    private val mapper = TransformOccurrenceMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(TransformOccurrenceEntity.PROVISION, mapper.toEntity(TransformOccurrence.Provision))
        assertEquals(TransformOccurrenceEntity.REVISION, mapper.toEntity(TransformOccurrence.Revision))
        assertEquals(TransformOccurrenceEntity.INQUISITION, mapper.toEntity(TransformOccurrence.Inquisition))
        assertEquals(TransformOccurrenceEntity.PREREQINJECTION, mapper.toEntity(TransformOccurrence.PreReqInjection))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(TransformOccurrence.Provision, mapper.toDto(TransformOccurrenceEntity.PROVISION))
        assertEquals(TransformOccurrence.Revision, mapper.toDto(TransformOccurrenceEntity.REVISION))
        assertEquals(TransformOccurrence.Inquisition, mapper.toDto(TransformOccurrenceEntity.INQUISITION))
        assertEquals(TransformOccurrence.PreReqInjection, mapper.toDto(TransformOccurrenceEntity.PREREQINJECTION))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            TransformOccurrence.Provision,
            TransformOccurrence.Revision,
            TransformOccurrence.Inquisition,
            TransformOccurrence.PreReqInjection
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = TransformOccurrenceEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}