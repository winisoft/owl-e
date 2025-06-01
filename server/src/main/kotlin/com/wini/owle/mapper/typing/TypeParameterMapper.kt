package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class TypeParameterMapper(
    private val daoDelegate: DaoDelegate<ULong, TypeParameter, TypeParameterEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, TypeParameter, TypeParameterEntity> {

    override fun toEntity(dto: TypeParameter): TypeParameterEntity {
        return daoDelegate.findOrCreate(dto) {
            typeNode = typeNodeMapper.toEntity(dto.typeDeclaration)
            position = dto.position
            parameterTypeNode = typeNodeMapper.toEntity(dto.parameterTypeDeclaration)
        }
    }

    override fun toDto(entity: TypeParameterEntity): TypeParameter {
        return TypeParameter(
            id = entity.id.value,
            typeDeclaration = typeNodeMapper.toDto(entity.typeNode),
            position = entity.position,
            parameterTypeDeclaration = typeNodeMapper.toDto(entity.parameterTypeNode)
        )
    }
}