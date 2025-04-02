package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.ComparisonOperatorDto

enum class ComparisonOperator {
    EQUALS, NOT_EQUALS, CONTAINS, GREATER_THAN, LESS_THAN;

    companion object {
        fun fromDto(dto: ComparisonOperatorDto) : ComparisonOperator {
            return when (dto) {
                ComparisonOperatorDto.Contains -> CONTAINS
                ComparisonOperatorDto.Equals -> EQUALS
                ComparisonOperatorDto.GreaterThan -> GREATER_THAN
                ComparisonOperatorDto.LessThan -> LESS_THAN
                ComparisonOperatorDto.NotEquals -> NOT_EQUALS
            }
        }
        fun ComparisonOperator.toDto(): ComparisonOperatorDto {
            return when (this) {
                EQUALS -> ComparisonOperatorDto.Equals
                NOT_EQUALS -> ComparisonOperatorDto.NotEquals
                CONTAINS -> ComparisonOperatorDto.Contains
                GREATER_THAN -> ComparisonOperatorDto.GreaterThan
                LESS_THAN -> ComparisonOperatorDto.LessThan
            }
        }
    }
}