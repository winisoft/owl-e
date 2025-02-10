package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeDeclarationDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeDefinitions
import com.wini.owle.entity.typing.TypeDeclarationDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.transforms.TransformationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeDeclarationsMapper(
    private val typeDefinitionsMapper: TypeDefinitionsMapper
) : DbMapper<TypeDeclarationDto, TypeDeclarationDao>() {

    override val applyProps: TypeDeclarationDao.(TypeDeclarationDto) -> Unit = { dto ->
        typeDefinition = typeDefinitionsMapper.run { dto.typeDefinition.toEntity() }
        alias = dto.alias
    }

    override fun TypeDeclarationDto.toEntity(): TypeDeclarationDao {
        return if (id == 0L) {
            TypeDeclarationDao.new {
                applyProps(this@toEntity)
            }
        } else {
            TypeDeclarationDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeDeclaration with id=$id not found")
        }
    }

    override fun TypeDeclarationDao.toDto(): TypeDeclarationDto {
        return TypeDeclarationDto(
            id = id.value,
            typeDefinition = typeDefinitionsMapper.run { typeDefinition.toDto() },
            alias = alias
        )
    }
}