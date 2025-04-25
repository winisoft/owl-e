package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeParameter
import com.wini.owle.entity.typing.TypeParameterEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TypeParametersMapper(
    private val daoFactory: DaoFactory<TypeParameterEntity, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeParameter, TypeParameterEntity>() {

    override fun toEntity(dto: TypeParameter): TypeParameterEntity {
        val applyProps: TypeParameterEntity.() -> Unit = {
            typeDeclarationEntity = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            position = dto.position
            parameterTypeDeclarationEntity = typeDeclarationsMapper.toEntity(dto.parameterTypeDeclaration)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeParameterEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeParameter with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeParameterEntity): TypeParameter {
        return TypeParameter(
            id = entity.id.value,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclarationEntity),
            position = entity.position,
            parameterTypeDeclaration = typeDeclarationsMapper.toDto(entity.parameterTypeDeclarationEntity)
        )
    }
}