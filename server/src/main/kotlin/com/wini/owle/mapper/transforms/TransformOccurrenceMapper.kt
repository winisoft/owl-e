package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformOccurrence
import com.wini.owle.entity.transforms.TransformOccurrenceEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper


class TransformOccurrenceMapper(
    private val daoDelegate: DaoDelegate<ULong, TransformOccurrence, TransformOccurrenceEntity>,
    private val transformMapper: TransformMapper,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, TransformOccurrence, TransformOccurrenceEntity> {

    override fun toEntity(dto: TransformOccurrence): TransformOccurrenceEntity {
        return daoDelegate.findOrCreate(dto) {
            transformation = transformMapper.toEntity(dto.transformation)
            occurrence = dto.occurrence
            resultType = typeNodeMapper.toEntity(dto.resultType)
        }
    }

    override fun toDto(entity: TransformOccurrenceEntity): TransformOccurrence {
        return TransformOccurrence(
            id = entity.id.value,
            transformation = transformMapper.toDto(entity.transformation),
            occurrence = entity.occurrence,
            resultType = typeNodeMapper.toDto(entity.resultType)
        )
    }
}