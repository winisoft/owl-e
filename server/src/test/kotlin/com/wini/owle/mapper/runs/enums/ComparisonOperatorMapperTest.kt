package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.ComparisonOperatorDto
import com.wini.owle.entity.runs.enums.ComparisonOperator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


open class ComparisonOperatorMapperTest {

    private val mapper = ComparisonOperatorMapper()

    @Test
    fun `toEntity should map each DTO to expected entity`() {
        assertEquals(ComparisonOperator.EQUALS, mapper.toEntity(ComparisonOperatorDto.Equals))
        assertEquals(ComparisonOperator.NOT_EQUALS, mapper.toEntity(ComparisonOperatorDto.NotEquals))
        assertEquals(ComparisonOperator.CONTAINS, mapper.toEntity(ComparisonOperatorDto.Contains))
        assertEquals(ComparisonOperator.GREATER_THAN, mapper.toEntity(ComparisonOperatorDto.GreaterThan))
        assertEquals(ComparisonOperator.LESS_THAN, mapper.toEntity(ComparisonOperatorDto.LessThan))
    }

    @Test
    fun `toDto should map each entity to expected DTO`() {
        assertEquals(ComparisonOperatorDto.Equals, mapper.toDto(ComparisonOperator.EQUALS))
        assertEquals(ComparisonOperatorDto.NotEquals, mapper.toDto(ComparisonOperator.NOT_EQUALS))
        assertEquals(ComparisonOperatorDto.Contains, mapper.toDto(ComparisonOperator.CONTAINS))
        assertEquals(ComparisonOperatorDto.GreaterThan, mapper.toDto(ComparisonOperator.GREATER_THAN))
        assertEquals(ComparisonOperatorDto.LessThan, mapper.toDto(ComparisonOperator.LESS_THAN))
    }

    @Test
    fun `round-trip from DTO to entity and back should be consistent`() {
        val allDtos = listOf(
            ComparisonOperatorDto.Equals,
            ComparisonOperatorDto.NotEquals,
            ComparisonOperatorDto.Contains,
            ComparisonOperatorDto.GreaterThan,
            ComparisonOperatorDto.LessThan
        )

        for (dto in allDtos) {
            val roundTripped = mapper.toDto(mapper.toEntity(dto))
            assertEquals(dto, roundTripped)
        }
    }

    @Test
    fun `round-trip from entity to DTO and back should be consistent`() {
        val allEntities = ComparisonOperator.entries

        for (entity in allEntities) {
            val roundTripped = mapper.toEntity(mapper.toDto(entity))
            assertEquals(entity, roundTripped, "Round-trip failed for entity: $entity")
        }
    }
}
