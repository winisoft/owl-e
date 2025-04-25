package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.TypeSchemaRef
import com.wini.owle.entity.typing.TypeSchemaRefEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class TypeSchemaRefsMapper(
    private val daoFactory: DaoFactory<TypeSchemaRefEntity, Long>,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TypeSchemaRef, TypeSchemaRefEntity>() {

    override fun toEntity(dto: TypeSchemaRef): TypeSchemaRefEntity {
        val applyProps: TypeSchemaRefEntity.() -> Unit = {
            parentRef = dto.parentRef?.let { toEntity(it) } // Recursive call
            name = dto.name
            typeDeclarationEntity = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TypeSchemaRefEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TypeSchemaRef with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TypeSchemaRefEntity): TypeSchemaRef {
        return TypeSchemaRef(
            id = entity.id.value,
            parentRef = entity.parentRef?.let { toDto(it) }, // Recursive call for parent
            name = entity.name,
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclarationEntity),
            description = entity.description,
            childRefs = emptyList<TypeSchemaRef>()
        )
    }
}
