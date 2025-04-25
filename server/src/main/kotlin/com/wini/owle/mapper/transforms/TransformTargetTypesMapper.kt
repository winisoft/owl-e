package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformTargetType
import com.wini.owle.entity.composteid.CompositeIdFactory
import com.wini.owle.entity.composteid.TransformTargetTypeIdFactory
import com.wini.owle.entity.transforms.TransformTargetTypeEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.CompositeID


class TransformTargetTypesMapper(
    private val daoFactory: DaoFactory<TransformTargetTypeEntity, CompositeID>,
    private val compositeIdFactory: CompositeIdFactory<TransformTargetType> = TransformTargetTypeIdFactory
) : DbMapper<TransformTargetType, TransformTargetTypeEntity>() {

    override fun toEntity(dto: TransformTargetType): TransformTargetTypeEntity {
        return compositeIdFactory.fromDtoInstance(dto).let { id ->
            TransformTargetTypeEntity.findById(id)
        } ?: TransformTargetTypeEntity.create(
            transformationId = dto.transformationId,
            typeDeclarationId = dto.typeDeclarationId
        )
    }

    override fun toDto(entity: TransformTargetTypeEntity) : TransformTargetType {
        return TransformTargetType(
            transformationId = 1,
            typeDeclarationId = 1
        )
    }
}