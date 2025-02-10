package com.wini.owle.mapper.transforms

import com.wini.owle.dto.AppliedTransformDto
import com.wini.owle.entity.*
import com.wini.owle.entity.TransformOccurrence.Companion.toDto
import com.wini.owle.entity.transforms.AppliedTransformArgDao
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeDeclarationsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import org.jetbrains.exposed.dao.id.EntityID


class AppliedTransformsMapper(
    private val transformationsMapper: TransformationsMapper,
    private val typeDeclarationsMapper: TypeDeclarationsMapper,
    private val dataPointsMapper: DataPointsMapper,
    private val appliedTransformArgMapper: AppliedTransformArgsMapper
) : DbMapper<AppliedTransformDto, AppliedTransformDao>() {

    override val applyProps: AppliedTransformDao.(AppliedTransformDto) -> Unit = { dto ->
        transformation = transformationsMapper.run { dto.transformation.toEntity() }
        occurrence = TransformOccurrence.fromDto(dto.occurrence)
        resultType = typeDeclarationsMapper.run { dto.resultType.toEntity() }
        dataPoint = dataPointsMapper.run { dto.dataPoint?.toEntity() }
    }

    override fun AppliedTransformDto.toEntity(): AppliedTransformDao {
        return if (id == 0L) {
            AppliedTransformDao.new { applyProps(this@toEntity) }
        } else {
            AppliedTransformDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("AppliedTransform with id=$id not found")
        }
    }

    override fun AppliedTransformDao.toDto(): AppliedTransformDto {
        return AppliedTransformDto(
            id = id.value,
            transformation = transformationsMapper.run { transformation.toDto() },
            occurrence = occurrence.toDto(),
            resultType = typeDeclarationsMapper.run { resultType.toDto() },
            dataPoint = dataPointsMapper.run { dataPoint?.toDto() }
        )
    }
}
