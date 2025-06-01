package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class TransformTargetTypeMapper(
    private val daoDelegate: DaoDelegate<CompositeID, TransformTargetType, TransformTargetTypeEntity>,
): DbMapper<CompositeID, TransformTargetType, TransformTargetTypeEntity> {

    override fun toEntity(dto: TransformTargetType): TransformTargetTypeEntity {
        return daoDelegate.findOrCreate(dto) {
//            transformation = dto.transformationId,
//            typeDeclaration = dto.typeDeclarationId
        }
    }

    override fun toDto(entity: TransformTargetTypeEntity) : TransformTargetType {
        return TransformTargetType(
            transformationId = 1UL,
            typeDeclarationId = 1UL
        )
    }
}