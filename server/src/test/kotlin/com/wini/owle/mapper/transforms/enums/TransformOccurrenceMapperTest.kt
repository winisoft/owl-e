package com.wini.owle.mapper.transforms.enums

import com.wini.owle.dto.transforms.TransformOccurrenceDto
import com.wini.owle.entity.transforms.enums.TransformOccurrence
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


open class TransformOccurrenceMapperTest {

    private val mapper = TransformOccurrenceMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(TransformOccurrence.PROVISION, mapper.toEntity(TransformOccurrenceDto.Provision))
        assertEquals(TransformOccurrence.REVISION, mapper.toEntity(TransformOccurrenceDto.Revision))
        assertEquals(TransformOccurrence.INQUISITION, mapper.toEntity(TransformOccurrenceDto.Inquisition))
        assertEquals(TransformOccurrence.PREREQINJECTION, mapper.toEntity(TransformOccurrenceDto.PreReqInjection))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(TransformOccurrenceDto.Provision, mapper.toDto(TransformOccurrence.PROVISION))
        assertEquals(TransformOccurrenceDto.Revision, mapper.toDto(TransformOccurrence.REVISION))
        assertEquals(TransformOccurrenceDto.Inquisition, mapper.toDto(TransformOccurrence.INQUISITION))
        assertEquals(TransformOccurrenceDto.PreReqInjection, mapper.toDto(TransformOccurrence.PREREQINJECTION))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            TransformOccurrenceDto.Provision,
            TransformOccurrenceDto.Revision,
            TransformOccurrenceDto.Inquisition,
            TransformOccurrenceDto.PreReqInjection
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = TransformOccurrence.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}