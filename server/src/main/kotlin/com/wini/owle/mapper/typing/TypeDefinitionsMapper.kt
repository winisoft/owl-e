package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDefinitionDto
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper


class TypeDefinitionsMapper(
    private val daoFactory: DaoFactory<TypeDefinitionDao, Long>,
    private val versionSpecsMapper: VersionSpecsMapper
): DbMapper<TypeDefinitionDto, TypeDefinitionDao>() {

    override fun toEntity(dto: TypeDefinitionDto): TypeDefinitionDao {
        val applyProps: TypeDefinitionDao.() -> Unit = {
            name = dto.name
            namespace = dto.namespace
            versionSpec = versionSpecsMapper.toEntity(dto.versionSpec)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeDefinitionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeDefinition with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeDefinitionDao): TypeDefinitionDto {
        return TypeDefinitionDto(
            id = entity.id.value,
            name = entity.name,
            namespace = entity.namespace,
            versionSpec = versionSpecsMapper.toDto(entity.versionSpec)
        )
    }
}