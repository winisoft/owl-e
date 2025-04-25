package com.wini.owle.mapper.typing.enums

import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import com.wini.owle.entity.typing.enums.VarianceCardinalityEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


open class VarianceCardinalityEntityMapperTest {

    private val mapper = VarianceCardinalityMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(VarianceCardinalityEntity.COVARIANT, mapper.toEntity(VarianceCardinalityDto.Covariant))
        assertEquals(VarianceCardinalityEntity.CONTRAVARIANT, mapper.toEntity(VarianceCardinalityDto.Contravariant))
        assertEquals(VarianceCardinalityEntity.INVARIANT, mapper.toEntity(VarianceCardinalityDto.Invariant))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(VarianceCardinalityDto.Covariant, mapper.toDto(VarianceCardinalityEntity.COVARIANT))
        assertEquals(VarianceCardinalityDto.Contravariant, mapper.toDto(VarianceCardinalityEntity.CONTRAVARIANT))
        assertEquals(VarianceCardinalityDto.Invariant, mapper.toDto(VarianceCardinalityEntity.INVARIANT))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            VarianceCardinalityDto.Covariant,
            VarianceCardinalityDto.Contravariant,
            VarianceCardinalityDto.Invariant,
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = VarianceCardinalityEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }

}