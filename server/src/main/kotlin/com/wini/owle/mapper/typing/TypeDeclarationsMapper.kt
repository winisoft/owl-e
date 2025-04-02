package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDeclarationDto
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
// Removed TransformationsMapper import as it's not directly used here


class TypeDeclarationsMapper(
    private val daoFactory: DaoFactory<TypeDeclarationDao, Long>,
    private val typeDefinitionsMapper: TypeDefinitionsMapper
): DbMapper<TypeDeclarationDto, TypeDeclarationDao>() {

    override fun toEntity(dto: TypeDeclarationDto): TypeDeclarationDao {
        val applyProps: TypeDeclarationDao.() -> Unit = {
            typeDefinition = typeDefinitionsMapper.toEntity(dto.typeDefinition)
            alias = dto.alias
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeDeclarationDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeDeclaration with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeDeclarationDao): TypeDeclarationDto {
        return TypeDeclarationDto(
            id = entity.id.value,
            typeDefinition = typeDefinitionsMapper.toDto(entity.typeDefinition),
            alias = entity.alias
        )
    }
}