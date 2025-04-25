package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
// Removed TypeDefinitionsMapper import as it's not directly used here


class TransformationArgsMapper(
    private val daoFactory: DaoFactory<TransformArgEntity, Long>,
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper
): DbMapper<TransformArg, TransformArgEntity>() {

    override fun toEntity(dto: TransformArg): TransformArgEntity {
        val applyProps: TransformArgEntity.() -> Unit = {
            transformation = transformationsMapper.toEntity(dto.transformation)
            typeDeclarationEntity = typeDeclarationsMapper.toEntity(dto.typeDeclaration)
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            TransformArgEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("TransformationArg with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: TransformArgEntity): TransformArg {
        return TransformArg(
            id = entity.id.value,
            transformation = transformationsMapper.toDto(entity.transformation),
            typeDeclaration = typeDeclarationsMapper.toDto(entity.typeDeclarationEntity),
            name = entity.name,
            description = entity.description
        )
    }
}