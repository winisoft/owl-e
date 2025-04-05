package com.wini.owle.mapper.typing.enums

import com.wini.owle.dto.typing.VarianceCardinalityDto
import com.wini.owle.entity.typing.enums.VarianceCardinality

class VarianceCardinalityMapper {

    fun toEntity(dto: VarianceCardinalityDto): VarianceCardinality {
        return when (dto) {
            VarianceCardinalityDto.Covariant -> VarianceCardinality.COVARIANT
            VarianceCardinalityDto.Contravariant -> VarianceCardinality.CONTRAVARIANT
            VarianceCardinalityDto.Invariant -> VarianceCardinality.INVARIANT
        }
    }

    fun toDto(entity: VarianceCardinality): VarianceCardinalityDto {
        return when (entity) {
            VarianceCardinality.COVARIANT -> VarianceCardinalityDto.Covariant
            VarianceCardinality.CONTRAVARIANT -> VarianceCardinalityDto.Contravariant
            VarianceCardinality.INVARIANT -> VarianceCardinalityDto.Invariant
        }
    }
}