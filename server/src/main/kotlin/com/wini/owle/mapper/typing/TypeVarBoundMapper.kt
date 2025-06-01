package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeVarBound
import com.wini.owle.entity.typing.TypeVarBoundEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TypeVarBoundMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TypeVarBound, TypeVarBoundEntity>,
    private val typeDeclarationMapper: TypeNodeMapper
): DbMapper<CompositeID, TypeVarBound, TypeVarBoundEntity> {

    override fun toEntity(dto: TypeVarBound): TypeVarBoundEntity {
        return daoDelegate.findOrCreate(dto) {
            typeVarNode = typeDeclarationMapper.toEntity(dto.typeVarNode)
            boundTypeNode = typeDeclarationMapper.toEntity(dto.boundTypeNode)
        }
    }

    override fun toDto(entity: TypeVarBoundEntity): TypeVarBound {
        return TypeVarBound(
            typeVarNode = typeDeclarationMapper.toDto(entity.typeVarNode),
            boundTypeNode = typeDeclarationMapper.toDto(entity.boundTypeNode)
        )
    }
}