package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformArg
import com.wini.owle.entity.transforms.TransformArgEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper
// Removed TypeDefinitionsMapper import as it's not directly used here


class TransformArgMapper(
    private val daoDelegate: DaoDelegate<ULong, TransformArg, TransformArgEntity>,
    private val transformMapper: TransformMapper,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, TransformArg, TransformArgEntity> {

    override fun toEntity(dto: TransformArg): TransformArgEntity {
        return daoDelegate.findOrCreate(dto) {
            transformation = transformMapper.toEntity(dto.transform)
            typeNode = typeNodeMapper.toEntity(dto.typeVarNode)
            name = dto.name
            description = dto.description
        }
    }

    override fun toDto(entity: TransformArgEntity): TransformArg {
        return TransformArg(
            id = entity.id.value,
            transformation = transformMapper.toDto(entity.transformation),
            typeNode = typeNodeMapper.toDto(entity.typeNode),
            name = entity.name,
            description = entity.description
        )
    }
}