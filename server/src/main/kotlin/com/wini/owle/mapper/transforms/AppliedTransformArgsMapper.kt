package com.wini.owle.mapper.transforms

import com.wini.owle.dto.AppliedTransformArgDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.transforms.AppliedTransformsMapper
import com.wini.owle.mapper.transforms.TransformationArgsMapper


class AppliedTransformArgsMapper(
    private val daoFactory: DaoFactory<AppliedTransformArgDao, Long>,
    private val appliedTransformsMapper: AppliedTransformsMapper,
    private val transformationArgsMapper: TransformationArgsMapper
): DbMapper<AppliedTransformArgDto, AppliedTransformArgDao>() {

    override fun toEntity(dto: AppliedTransformArgDto): AppliedTransformArgDao {
        val applyProps: AppliedTransformArgDao.() -> Unit = {
            appliedTransform = appliedTransformsMapper.toEntity(dto.appliedTransform)
            transformationArg = transformationArgsMapper.toEntity(dto.transformationArg)
            value = dto.value
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            AppliedTransformArgDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("AppliedTransformArg with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: AppliedTransformArgDao): AppliedTransformArgDto {
        return AppliedTransformArgDto(
            id = entity.id.value,
            appliedTransform = appliedTransformsMapper.toDto(entity.appliedTransform),
            transformationArg = transformationArgsMapper.toDto(entity.transformationArg),
            value = entity.value
        )
    }
}