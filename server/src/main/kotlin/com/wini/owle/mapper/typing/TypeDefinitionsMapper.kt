package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeDefinitionDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.VersionSpecs
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.entity.typing.TypeDefinitionDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.VersionSpecsMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeDefinitionsMapper(
    private val versionSpecsMapper: VersionSpecsMapper
) : DbMapper<TypeDefinitionDto, TypeDefinitionDao>() {

    override val applyProps: TypeDefinitionDao.(TypeDefinitionDto) -> Unit = { dto ->
        name = dto.name
        namespace = dto.namespace
        versionSpec = versionSpecsMapper.run { dto.versionSpec.toEntity() }
    }

    override fun TypeDefinitionDto.toEntity(): TypeDefinitionDao {
        return if (id == 0L) {
            TypeDefinitionDao.new { applyProps(this@toEntity) }
        } else {
            TypeDefinitionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeDefinition with id=$id not found")
        }
    }

    override fun TypeDefinitionDao.toDto(): TypeDefinitionDto {
        return TypeDefinitionDto(
            id = id.value,
            name = name,
            namespace = namespace,
            versionSpec = versionSpecsMapper.run { versionSpec.toDto() }
        )
    }
}