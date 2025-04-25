package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeBound
import com.wini.owle.entity.typing.TypeBoundEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TypeBoundsMapper(
    private val daoFactory: DaoFactory<TypeBoundEntity, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper
): DbMapper<TypeBound, TypeBoundEntity>() {

    override fun toEntity(dto: TypeBound): TypeBoundEntity {
        val applyProps: TypeBoundEntity.() -> Unit = {
            typeVariableDeclaration = typeDeclarationMapper.toEntity(dto.typeVariableDeclaration)
            boundTypeDeclarationEntity = typeDeclarationMapper.toEntity(dto.boundTypeDeclaration)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeBoundEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeBound with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeBoundEntity): TypeBound {
        return TypeBound(
            id = entity.id.value,
            typeVariableDeclaration = typeDeclarationMapper.toDto(entity.typeVariableDeclaration),
            boundTypeDeclaration = typeDeclarationMapper.toDto(entity.boundTypeDeclarationEntity)
        )
    }
}