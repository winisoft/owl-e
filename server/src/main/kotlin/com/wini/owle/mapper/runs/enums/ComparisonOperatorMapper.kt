package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.ComparisonOperator
import com.wini.owle.entity.runs.enums.ComparisonOperatorEntity

class ComparisonOperatorMapper {

    fun toEntity(dto: ComparisonOperator) : ComparisonOperatorEntity {
        return when (dto) {
            ComparisonOperator.CONTAINS -> ComparisonOperatorEntity.CONTAINS
            ComparisonOperator.EQUALS -> ComparisonOperatorEntity.EQUALS
            ComparisonOperator.GREATER_THAN -> ComparisonOperatorEntity.GREATER_THAN
            ComparisonOperator.LESS_THAN -> ComparisonOperatorEntity.LESS_THAN
            ComparisonOperator.NOT_EQUALS -> ComparisonOperatorEntity.NOT_EQUALS
        }
    }

    fun toDto(entity: ComparisonOperatorEntity): ComparisonOperator {
        return when (entity) {
            ComparisonOperatorEntity.EQUALS -> ComparisonOperator.EQUALS
            ComparisonOperatorEntity.NOT_EQUALS -> ComparisonOperator.NOT_EQUALS
            ComparisonOperatorEntity.CONTAINS -> ComparisonOperator.CONTAINS
            ComparisonOperatorEntity.GREATER_THAN -> ComparisonOperator.GREATER_THAN
            ComparisonOperatorEntity.LESS_THAN -> ComparisonOperator.LESS_THAN
        }
    }

}