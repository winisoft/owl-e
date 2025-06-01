package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeVarConstraint
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.typing.TypeVarConstraintEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TypeVarConstraintMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TypeVarConstraint, TypeVarConstraintEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<CompositeID, TypeVarConstraint, TypeVarConstraintEntity> {

    override fun toEntity(dto: TypeVarConstraint): TypeVarConstraintEntity {
        return daoDelegate.findOrCreate(dto) {
            typeVarNode = typeNodeMapper.toEntity(dto.reference)
            constraintNode = typeNodeMapper.toEntity(dto.constraint)
        }
    }

    override fun toDto(entity: TypeVarConstraintEntity): TypeVarConstraint {
        return TypeVarConstraint(
            reference = typeNodeMapper.toDto(entity.typeVarNode),
            constraint = typeNodeMapper.toDto(entity.constraintNode)
        )
    }
}