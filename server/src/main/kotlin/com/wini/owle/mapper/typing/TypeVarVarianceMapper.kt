package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeVarVariance
import com.wini.owle.entity.typing.TypeVarVarianceEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TypeVarVarianceMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TypeVarVariance, TypeVarVarianceEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<CompositeID, TypeVarVariance, TypeVarVarianceEntity> {

    override fun toEntity(dto: TypeVarVariance): TypeVarVarianceEntity {
        return daoDelegate.findOrCreate(dto) {
            typeVarNode = typeNodeMapper.toEntity(dto.typeNode)
            varianceCardinality = dto.cardinality
        }
    }

    override fun toDto(entity: TypeVarVarianceEntity): TypeVarVariance {
        return TypeVarVariance(
            typeNode = typeNodeMapper.toDto(entity.typeVarNode),
            cardinality = entity.varianceCardinality
        )
    }
}