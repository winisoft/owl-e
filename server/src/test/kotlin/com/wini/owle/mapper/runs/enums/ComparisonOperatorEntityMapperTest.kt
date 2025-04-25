package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.entity.runs.enums.ComparisonOperatorEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


open class ComparisonOperatorEntityMapperTest {

    private val mapper = ComparisonOperatorMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ComparisonOperatorEntity.EQUALS, mapper.toEntity(ComparisonOperator.Equals))
        assertEquals(ComparisonOperatorEntity.NOT_EQUALS, mapper.toEntity(ComparisonOperator.NotEquals))
        assertEquals(ComparisonOperatorEntity.CONTAINS, mapper.toEntity(ComparisonOperator.Contains))
        assertEquals(ComparisonOperatorEntity.GREATER_THAN, mapper.toEntity(ComparisonOperator.GreaterThan))
        assertEquals(ComparisonOperatorEntity.LESS_THAN, mapper.toEntity(ComparisonOperator.LessThan))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ComparisonOperator.Equals, mapper.toDto(ComparisonOperatorEntity.EQUALS))
        assertEquals(ComparisonOperator.NotEquals, mapper.toDto(ComparisonOperatorEntity.NOT_EQUALS))
        assertEquals(ComparisonOperator.Contains, mapper.toDto(ComparisonOperatorEntity.CONTAINS))
        assertEquals(ComparisonOperator.GreaterThan, mapper.toDto(ComparisonOperatorEntity.GREATER_THAN))
        assertEquals(ComparisonOperator.LessThan, mapper.toDto(ComparisonOperatorEntity.LESS_THAN))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            ComparisonOperator.Equals,
            ComparisonOperator.NotEquals,
            ComparisonOperator.Contains,
            ComparisonOperator.GreaterThan,
            ComparisonOperator.LessThan
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = ComparisonOperatorEntity.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }
}
