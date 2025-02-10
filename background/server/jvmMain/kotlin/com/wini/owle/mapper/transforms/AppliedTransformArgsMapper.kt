package com.wini.owle.mapper.transforms

import com.wini.owle.dto.AppliedTransformArgDto
import com.wini.owle.entity.AppliedTransformArgs
import com.wini.owle.entity.AppliedTransforms
import com.wini.owle.entity.TransformationArgs
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class AppliedTransformArgsMapper(
    private val appliedTransformsMapper: AppliedTransformsMapper,
    private val transformationArgsMapper: TransformationArgsMapper
) : DbMapper<AppliedTransformArgDto, AppliedTransformArgDao>() {

    override val applyProps: AppliedTransformArgDao.(AppliedTransformArgDto) -> Unit = { dto ->
        appliedTransform = appliedTransformsMapper.run { dto.appliedTransform.toEntity() }
        transformationArg = transformationArgsMapper.run { dto.transformationArg.toEntity() }
        value = dto.value
    }

    override fun AppliedTransformArgDto.toEntity(): AppliedTransformArgDao {
        return if (id == 0L) {
            AppliedTransformArgDao.new { applyProps(this@toEntity) }
        } else {
            AppliedTransformArgDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("AppliedTransformArg with id=$id not found")
        }
    }

    override fun AppliedTransformArgDao.toDto(): AppliedTransformArgDto {
        return AppliedTransformArgDto(
            id = id.value,
            appliedTransform = appliedTransformsMapper.run { appliedTransform.toDto() },
            transformationArg = transformationArgsMapper.run { transformationArg.toDto() },
            value = value
        )
    }
}