package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformArg
import com.wini.owle.entity.transforms.AppliedTransformArgEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class AppliedTransformArgsMapper(
    private val daoFactory: DaoFactory<AppliedTransformArgEntity, Long>,
    private val appliedTransformsMapper: AppliedTransformsMapper,
    private val transformationArgsMapper: TransformationArgsMapper
): DbMapper<AppliedTransformArg, AppliedTransformArgEntity>() {

    override fun toEntity(dto: AppliedTransformArg): AppliedTransformArgEntity {
        val applyProps: AppliedTransformArgEntity.() -> Unit = {
            appliedTransformEntity = appliedTransformsMapper.toEntity(dto.appliedTransform)
            transformationArg = transformationArgsMapper.toEntity(dto.transformationArg)
            value = dto.value
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            AppliedTransformArgEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("AppliedTransformArg with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: AppliedTransformArgEntity): AppliedTransformArg {
        return AppliedTransformArg(
            id = entity.id.value,
            appliedTransform = appliedTransformsMapper.toDto(entity.appliedTransformEntity),
            transformationArg = transformationArgsMapper.toDto(entity.transformationArg),
            value = entity.value
        )
    }
}