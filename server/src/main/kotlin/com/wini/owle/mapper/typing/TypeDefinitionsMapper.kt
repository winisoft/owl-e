package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeDefinition
import com.wini.owle.entity.typing.TypeDefinitionEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper


class TypeDefinitionsMapper(
    private val daoFactory: DaoFactory<TypeDefinitionEntity, Long>,
    private val versionSpecsMapper: VersionSpecsMapper
): DbMapper<TypeDefinition, TypeDefinitionEntity>() {

    override fun toEntity(dto: TypeDefinition): TypeDefinitionEntity {
        val applyProps: TypeDefinitionEntity.() -> Unit = {
            name = dto.name
            namespace = dto.namespace
            versionSpec = versionSpecsMapper.toEntity(dto.versionSpec)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeDefinitionEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeDefinition with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeDefinitionEntity): TypeDefinition {
        return TypeDefinition(
            id = entity.id.value,
            name = entity.name,
            namespace = entity.namespace,
            versionSpec = versionSpecsMapper.toDto(entity.versionSpec)
        )
    }
}