package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclaration
import com.wini.owle.entity.typing.TypeDeclarationEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
// Removed TransformationsMapper import as it's not directly used here


class TypeDeclarationsMapper(
    private val daoFactory: DaoFactory<TypeDeclarationEntity, Long>,
    private val typeDefinitionsMapper: TypeDefinitionsMapper
): DbMapper<TypeDeclaration, TypeDeclarationEntity>() {

    override fun toEntity(dto: TypeDeclaration): TypeDeclarationEntity {
        val applyProps: TypeDeclarationEntity.() -> Unit = {
            typeDefinitionEntity = typeDefinitionsMapper.toEntity(dto.typeDefinition)
            alias = dto.alias
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeDeclarationEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeDeclaration with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeDeclarationEntity): TypeDeclaration {
        return TypeDeclaration(
            id = entity.id.value,
            typeDefinition = typeDefinitionsMapper.toDto(entity.typeDefinitionEntity),
            alias = entity.alias
        )
    }
}