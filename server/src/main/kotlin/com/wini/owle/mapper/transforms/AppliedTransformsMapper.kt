package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransform
import com.wini.owle.entity.transforms.AppliedTransformEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.transforms.enums.TransformOccurrenceMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper


class AppliedTransformsMapper(
    private val daoFactory: DaoFactory<AppliedTransformEntity, Long>,
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val dataPointsMapper: DataPointsMapper,
    private val transformOccurrenceMapper: TransformOccurrenceMapper
): DbMapper<AppliedTransform, AppliedTransformEntity>() {

    override fun toEntity(dto: AppliedTransform): AppliedTransformEntity {
        val applyProps: AppliedTransformEntity.() -> Unit = {
            transformation = transformationsMapper.toEntity(dto.transformation)
            occurrence = transformOccurrenceMapper.toEntity(dto.occurrence)
            resultType = typeDeclarationsMapper.toEntity(dto.resultType)
            dataPointEntity = dto.dataPoint?.let { dataPointsMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            AppliedTransformEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("AppliedTransform with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: AppliedTransformEntity): AppliedTransform {
        return AppliedTransform(
            id = entity.id.value,
            transformation = transformationsMapper.toDto(entity.transformation),
            occurrence = transformOccurrenceMapper.toDto(entity.occurrence),
            resultType = typeDeclarationsMapper.toDto(entity.resultType),
            dataPoint = entity.dataPointEntity?.let { dataPointsMapper.toDto(it) }
        )
    }
}