package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.Transform
import com.wini.owle.entity.transforms.TransformEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper


class TransformationsMapper(
    private val daoFactory: DaoFactory<TransformEntity, Long>,
    private val typeDeclarationMapper: TypeDeclarationsMapper
): DbMapper<Transform, TransformEntity>() {

    override fun toEntity(dto: Transform): TransformEntity {
        val applyProps: TransformEntity.() -> Unit = {
            name = dto.name
            description = dto.description
            examples = dto.examples
            returnsTypeDeclarationEntity = dto.returnsType?.let { typeDeclarationMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TransformEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Transformation with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TransformEntity): Transform {
        return Transform(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            examples = entity.examples,
            returnsType = entity.returnsTypeDeclarationEntity?.let { typeDeclarationMapper.toDto(it) }
        )
    }
}