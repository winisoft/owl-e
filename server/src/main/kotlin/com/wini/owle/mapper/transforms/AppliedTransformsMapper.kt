package com.wini.owle.mapper.transforms

import com.wini.owle.dto.AppliedTransformDto
import com.wini.owle.entity.TransformOccurrence
import com.wini.owle.entity.TransformOccurrence.Companion.toDto // Needed for entity.occurrence.toDto()
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper // Assumed updated
import com.wini.owle.mapper.workflows.DataPointsMapper // Assumed updated
import com.wini.owle.mapper.transforms.TransformationsMapper // Assumed updated


class AppliedTransformsMapper(
    private val daoFactory: DaoFactory<AppliedTransformDao, Long>,
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val dataPointsMapper: DataPointsMapper
): DbMapper<AppliedTransformDto, AppliedTransformDao>() {

    override fun toEntity(dto: AppliedTransformDto): AppliedTransformDao {
        val applyProps: AppliedTransformDao.() -> Unit = {
            transformation = transformationsMapper.toEntity(dto.transformation)
            occurrence = TransformOccurrence.fromDto(dto.occurrence) // Use companion method
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
            occurrence = entity.occurrence.toDto(), // Use extension function from companion
            resultType = typeDeclarationsMapper.toDto(entity.resultType),
            dataPoint = entity.dataPoint?.let { dataPointsMapper.toDto(it) }
        )
    }
}