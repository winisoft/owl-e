package com.wini.owle.entity.typing

import com.wini.owle.dto.typing.VarianceCardinalityDto

enum class VarianceCardinality {
    COVARIANT, CONTRAVARIANT, INVARIANT;

    companion object {
        fun fromDto(dto: VarianceCardinalityDto): VarianceCardinality {
            return when (dto) {
                VarianceCardinalityDto.Covariant -> COVARIANT
                VarianceCardinalityDto.Contravariant -> CONTRAVARIANT
                VarianceCardinalityDto.Invariant -> INVARIANT
            }
        }
        fun VarianceCardinality.toDto(): VarianceCardinalityDto {
            return when (this) {
                COVARIANT -> VarianceCardinalityDto.Covariant
                CONTRAVARIANT -> VarianceCardinalityDto.Contravariant
                INVARIANT -> VarianceCardinalityDto.Invariant
            }
        }
    }
}