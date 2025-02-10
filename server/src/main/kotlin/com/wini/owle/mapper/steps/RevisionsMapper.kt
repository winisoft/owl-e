package com.wini.owle.mapper.steps

import com.wini.owle.dto.RevisionDto
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.Revisions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.Transformations
import com.wini.owle.entity.steps.RevisionDao
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.transforms.TransformationsMapper
import org.jetbrains.exposed.dao.id.EntityID


class RevisionsMapper(
    private val dataPointMapper: DataPointsMapper,
    private val stepMapper: StepsMapper
) : DbMapper<RevisionDto, RevisionDao>() {

    override val applyProps: RevisionDao.(RevisionDto) -> Unit = { dto ->
        dataPoint = dataPointMapper.run { dto.dataPoint.toEntity() }
        step = stepMapper.run { dto.step.toEntity() }
    }

    override fun RevisionDto.toEntity(): RevisionDao {
        return if (id == 0L) {
            RevisionDao.new { applyProps(this@toEntity) }
        } else {
            RevisionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Revision with id=$id not found")
        }
    }

    override fun RevisionDao.toDto(): RevisionDto {
        return RevisionDto(
            id = id.value,
            dataPoint = dataPointMapper.run { dataPoint.toDto() },
            step = stepMapper.run { step.toDto() }
        )
    }
}