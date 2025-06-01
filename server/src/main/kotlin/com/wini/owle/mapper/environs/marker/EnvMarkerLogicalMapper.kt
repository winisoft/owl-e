package com.wini.owle.mapper.environs.marker

import com.wini.owle.dto.environs.marker.EnvMarkerLogical
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.marker.EnvMarkerLogicalEntity
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.pkg.PackageDistRequirementMapper


class EnvMarkerLogicalMapper(
    private val daoDelegate: DaoDelegate<ULong, EnvMarkerLogical, EnvMarkerLogicalEntity>,
    val packageDistRequirementMapper: PackageDistRequirementMapper,
    val envMarkerExprMapper: EnvMarkerExpressionMapper
): DbMapper<ULong, EnvMarkerLogical, EnvMarkerLogicalEntity> {

    override fun toEntity(dto: EnvMarkerLogical): EnvMarkerLogicalEntity {
        val applyProps: EnvMarkerLogicalEntity.() -> Unit = {
            expression = envMarkerExprMapper.toEntity(dto.expression)
            operator = dto.operator
            left = envMarkerExprMapper.toEntity(dto.left)
            right = envMarkerExprMapper.toEntity(dto.right)
        }
        return daoDelegate.findOrCreate(dto, applyProps)
    }

    override fun toDto(entity: EnvMarkerLogicalEntity): EnvMarkerLogical {
        return EnvMarkerLogical(
            id = entity.id.value,
            expression = envMarkerExprMapper.toDto(entity.expression),
            operator = entity.operator,
            left = envMarkerExprMapper.toDto(entity.left),
            right = envMarkerExprMapper.toDto(entity.right)
        )
    }
}