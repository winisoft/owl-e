package com.wini.owle.mapper.transforms

import com.wini.owle.dto.transforms.AppliedTransformDto
import com.wini.owle.entity.transforms.enums.TransformOccurrence
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.transforms.enums.TransformOccurrenceMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper


class AppliedTransformsMapper(
    private val daoFactory: DaoFactory<AppliedTransformDao, Long>,
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val dataPointsMapper: DataPointsMapper,
    private val transformOccurrenceMapper: TransformOccurrenceMapper
): DbMapper<AppliedTransformDto, AppliedTransformDao>() {

    override fun toEntity(dto: AppliedTransformDto): AppliedTransformDao {
        val applyProps: AppliedTransformDao.() -> Unit = {
            transformation = transformationsMapper.toEntity(dto.transformation)
            occurrence = transformOccurrenceMapper.toEntity(dto.occurrence)
            resultType = typeDeclarationsMapper.toEntity(dto.resultType)
            dataPoint = dto.dataPoint?.let { dataPointsMapper.toEntity(it) }
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            AppliedTransformDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("AppliedTransform with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: AppliedTransformDao): AppliedTransformDto {
        return AppliedTransformDto(
            id = entity.id.value,
            transformation = transformationsMapper.toDto(entity.transformation),
            occurrence = transformOccurrenceMapper.toDto(entity.occurrence),
            resultType = typeDeclarationsMapper.toDto(entity.resultType),
            dataPoint = entity.dataPoint?.let { dataPointsMapper.toDto(it) }
        )
    }
}