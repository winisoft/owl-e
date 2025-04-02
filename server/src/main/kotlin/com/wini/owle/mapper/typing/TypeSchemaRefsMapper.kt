package com.wini.owle.mapper.typing

import com.wini.owle.dto.TypeSchemaRefDto
import com.wini.owle.entity.TypeDeclarations
import com.wini.owle.entity.TypeSchemaRefs
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.typing.TypeSchemaRefDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class TypeSchemaRefsMapper(
    private val daoFactory: DaoFactory<TypeSchemaRefDao, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeSchemaRefDto, TypeSchemaRefDao>() {

    override fun toEntity(dto: TypeSchemaRefDto): TypeSchemaRefDao {
        val applyProps: TypeSchemaRefDao.() -> Unit = {
            parentRef = dto.parentRef?.let { toEntity(it) } // Recursive call
            name = dto.name
            typeDeclaration = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeSchemaRefDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeSchemaRef with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeSchemaRefDao): TypeSchemaRefDto {
        return TypeSchemaRefDto(
            id = entity.id.value,
            parentRef = entity.parentRef?.let { toDto(it) }, // Recursive call for parent
            name = entity.name,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclaration),
            description = entity.description,
            childRefs = emptyList<TypeSchemaRefDto>()
        )
    }
}
