package com.wini.owle.mapper.typing.enums

import com.wini.owle.dto.typing.enums.VarianceCardinalityDto
import com.wini.owle.entity.typing.enums.VarianceCardinalityEntity

class VarianceCardinalityMapper {

    fun toEntity(dto: VarianceCardinalityDto): VarianceCardinalityEntity {
        return when (dto) {
            VarianceCardinalityDto.COVARIANT -> VarianceCardinalityEntity.COVARIANT
            VarianceCardinalityDto.CONTRAVARIANT -> VarianceCardinalityEntity.CONTRAVARIANT
            VarianceCardinalityDto.INVARIANT -> VarianceCardinalityEntity.INVARIANT
        }
    }

    fun toDto(entity: VarianceCardinalityEntity): VarianceCardinalityDto {
        return when (entity) {
            VarianceCardinalityEntity.COVARIANT -> VarianceCardinalityDto.COVARIANT
            VarianceCardinalityEntity.CONTRAVARIANT -> VarianceCardinalityDto.CONTRAVARIANT
            VarianceCardinalityEntity.INVARIANT -> VarianceCardinalityDto.INVARIANT
        }
    }
}