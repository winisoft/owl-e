package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.ComparisonOperatorDto
import com.wini.owle.entity.runs.enums.ComparisonOperator

class ComparisonOperatorMapper {

    fun toEntity(dto: ComparisonOperatorDto) : ComparisonOperator {
        return when (dto) {
            ComparisonOperatorDto.Contains -> ComparisonOperator.CONTAINS
            ComparisonOperatorDto.Equals -> ComparisonOperator.EQUALS
            ComparisonOperatorDto.GreaterThan -> ComparisonOperator.GREATER_THAN
            ComparisonOperatorDto.LessThan -> ComparisonOperator.LESS_THAN
            ComparisonOperatorDto.NotEquals -> ComparisonOperator.NOT_EQUALS
            else -> throw IllegalArgumentException("Unsupported member for ComparisonOperatorDto: ${dto.id}")
        }
    }

    fun toDto(entity: ComparisonOperator): ComparisonOperatorDto {
        return when (entity) {
            ComparisonOperator.EQUALS -> ComparisonOperatorDto.Equals
            ComparisonOperator.NOT_EQUALS -> ComparisonOperatorDto.NotEquals
            ComparisonOperator.CONTAINS -> ComparisonOperatorDto.Contains
            ComparisonOperator.GREATER_THAN -> ComparisonOperatorDto.GreaterThan
            ComparisonOperator.LESS_THAN -> ComparisonOperatorDto.LessThan
        }
    }

}