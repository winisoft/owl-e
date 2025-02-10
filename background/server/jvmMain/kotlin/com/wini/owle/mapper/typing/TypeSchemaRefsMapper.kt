package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeSchemaRefDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeSchemaRefs
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeSchemaRefsMapper(
    private val typeDeclarationsMapper: TypeDeclarationsMapper
) : DbMapper<TypeSchemaRefDto, TypeSchemaRefDao>() {

    override val applyProps: TypeSchemaRefDao.(TypeSchemaRefDto) -> Unit = { dto ->
        parentRef = null
        name = dto.name
        typeDeclaration = typeDeclarationsMapper.run { dto.typeDeclaration.toEntity() }
        description = dto.description
    }

    override fun TypeSchemaRefDto.toEntity(): TypeSchemaRefDao {
        return if (id == 0L) {
            TypeSchemaRefDao.new { applyProps(this@toEntity) }
        } else {
            TypeSchemaRefDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("TypeSchemaRef with id=$id not found")
        }
    }

    override fun TypeSchemaRefDao.toDto(): TypeSchemaRefDto {
        return TypeSchemaRefDto(
            id = id.value,
            parentRef = null,
            name = name,
            typeDeclaration = typeDeclarationsMapper.run { typeDeclaration.toDto() },
            description = description,
            childRefs = TypeSchemaRefDao.find { TypeSchemaRefs.parentRef eq id }
                .map { it.toDto() }
        )
    }
}