package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper


class TransformMapper(
    private val daoDelegate: DaoDelegate<ULong, Transform, TransformEntity>,
    private val typeDeclarationMapper: TypeNodeMapper
): DbMapper<ULong, Transform, TransformEntity> {

    override fun toEntity(dto: Transform): TransformEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            description = dto.description
            examples = dto.examples
        }
    }

    override fun toDto(entity: TransformEntity): Transform {
        return Transform(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            examples = entity.examples
        )
    }
}