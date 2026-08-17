package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.TransformOccurrenceArg
import com.wini.owle.entity.transforms.TransformOccurrenceArgEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper


class TransformOccurrenceArgMapper(
    private val daoDelegate: DaoDelegate<ULong, TransformOccurrenceArg, TransformOccurrenceArgEntity>,
    private val transformOccurrenceMapper: TransformOccurrenceMapper,
    private val transformArgMapper: TransformArgMapper
): DbMapper<ULong, TransformOccurrenceArg, TransformOccurrenceArgEntity> {

    override fun toEntity(dto: TransformOccurrenceArg): TransformOccurrenceArgEntity {
        return daoDelegate.findOrCreate(dto) {
            transformOccurrenceEntity = this@TransformOccurrenceArgMapper.transformOccurrenceMapper.toEntity(dto.transformOccurrence)
            transformationArg = transformArgMapper.toEntity(dto.transformArg)
            value = dto.value
        }
    }

    override fun toDto(entity: TransformOccurrenceArgEntity): TransformOccurrenceArg {
        return TransformOccurrenceArg(
            id = entity.id.value,
            transformOccurrence = transformOccurrenceMapper.toDto(entity.transformOccurrenceEntity),
            transformationArg = transformArgMapper.toDto(entity.transformationArg),
            value = entity.value
        )
    }
}