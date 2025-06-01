package com.wini.owle.mapper.typing

import com.wini.owle.dto.typing.ClassInheritance
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.typing.TypeNodeRelationEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class ClassInheritanceMapper(
    private val daoDelegate: DaoDelegate<CompositeID, ClassInheritance, TypeNodeRelationEntity>,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<CompositeID, ClassInheritance, TypeNodeRelationEntity> {

    override fun toEntity(dto: ClassInheritance): TypeNodeRelationEntity {
        return daoDelegate.findOrCreate(dto) {
            parentNode = typeNodeMapper.toEntity(dto.parent)
            childNode = typeNodeMapper.toEntity(dto.child)
        }
    }

    override fun toDto(entity: TypeNodeRelationEntity): ClassInheritance {
        return ClassInheritance(
            parent = typeNodeMapper.toDto(entity.parentNode),
            child = typeNodeMapper.toDto(entity.childNode)
        )
    }
}