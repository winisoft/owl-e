package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.entity.typing.TypeNodeEntity
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class TypeSchemaRefMapper(
    private val daoDelegate: DaoDelegate<ULong, TypeSchemaRef, TypeSchemaRefEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, TypeSchemaRef, TypeSchemaRefEntity> {

    override fun toEntity(dto: TypeSchemaRef): TypeSchemaRefEntity {
        return daoDelegate.findOrCreate(dto) {
            parentRef = dto.parentRef?.let { toEntity(it) }
            name = dto.name
            typeNode = typeNodeMapper.toEntity(dto.typeDeclaration)
            description = dto.description
        }
    }

    override fun toDto(entity: TypeSchemaRefEntity): TypeSchemaRef {
        return TypeSchemaRef(
            id = entity.id.value,
            parentRef = entity.parentRef?.let { toDto(it) }, // Recursive call for parent
            name = entity.name,
            typeDeclaration = typeNodeMapper.toDto(entity.typeNode),
            description = entity.description,
            childRefs = emptyList<TypeSchemaRef>()
        )
    }
}
