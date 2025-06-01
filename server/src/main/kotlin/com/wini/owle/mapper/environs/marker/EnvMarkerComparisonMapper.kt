package com.wini.owle.mapper.environs.marker

import com.wini.owle.dto.environs.marker.EnvMarkerComparison
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.marker.EnvMarkerComparisonEntity
import com.wini.owle.mapper.DbMapper


class EnvMarkerComparisonMapper(
    private val daoDelegate: DaoDelegate<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity>,
    val envMarkerExprMapper: EnvMarkerExpressionMapper
): DbMapper<ULong, EnvMarkerComparison, EnvMarkerComparisonEntity> {

    override fun toEntity(dto: EnvMarkerComparison): EnvMarkerComparisonEntity {
        return daoDelegate.findOrCreate(dto) {
            expression = envMarkerExprMapper.toEntity(dto.expression)
            variable = dto.variable
            operator = dto.operator
            value = dto.value
        }
    }

    override fun toDto(entity: EnvMarkerComparisonEntity): EnvMarkerComparison {
        return EnvMarkerComparison(
            id = entity.id.value,
            expression = envMarkerExprMapper.toDto(entity.expression),
            variable = entity.variable,
            operator = entity.operator,
            value = entity.value
        )
    }

}
