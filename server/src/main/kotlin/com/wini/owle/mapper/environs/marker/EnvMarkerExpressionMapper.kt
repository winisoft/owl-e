package com.wini.owle.mapper.environs.marker

import com.wini.owle.dto.environs.marker.EnvMarkerExpression
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.environs.marker.EnvMarkerExpressionEntity
import com.wini.owle.mapper.DbMapper

import com.wini.owle.mapper.environs.pkg.PackageDistRequirementMapper


class EnvMarkerExpressionMapper(
    private val daoDelegate: DaoDelegate<ULong, EnvMarkerExpression, EnvMarkerExpressionEntity>,
    val packageDistRequirementMapper: PackageDistRequirementMapper
): DbMapper<ULong, EnvMarkerExpression, EnvMarkerExpressionEntity> {

    override fun toEntity(dto: EnvMarkerExpression): EnvMarkerExpressionEntity {
        val applyProps: EnvMarkerExpressionEntity.() -> Unit = {
            parent = dto.parent?.run(::toEntity)
            mode = dto.mode
        }
        return daoDelegate.findOrCreate(dto, applyProps)
    }

    override fun toDto(entity: EnvMarkerExpressionEntity): EnvMarkerExpression {
        return EnvMarkerExpression(
            id = entity.id.value,
            parent = entity.parent?.run(::toDto),
            mode = entity.mode
        )
    }
}