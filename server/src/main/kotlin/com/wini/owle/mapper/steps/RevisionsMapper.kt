package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.RevisionDto
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper // Assumed updated
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper // Assumed updated


class RevisionsMapper(
    private val daoFactory: DaoFactory<RevisionDao, Long>,
    private val dataPointMapper: DataPointsMapper,
    private val stepMapper: StepsMapper
): DbMapper<RevisionDto, RevisionDao>() {

    override fun toEntity(dto: RevisionDto): RevisionDao {
        val applyProps: RevisionDao.() -> Unit = {
            dataPoint = dataPointMapper.toEntity(dto.dataPoint)
            step = stepMapper.toEntity(dto.step)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            RevisionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Revision with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: RevisionDao): RevisionDto {
        return RevisionDto(
            id = entity.id.value,
            dataPoint = dataPointMapper.toDto(entity.dataPoint),
            step = stepMapper.toDto(entity.step)
        )
    }
}