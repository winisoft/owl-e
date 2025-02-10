package com.wini.owle.mapper.steps

import com.wini.owle.dto.InquisitionDto
import com.wini.owle.entity.AppliedTransforms
import com.wini.owle.entity.DataPoints
import com.wini.owle.entity.Inquisitions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.entity.transforms.AppliedTransformDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.transforms.AppliedTransformsMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import org.jetbrains.exposed.dao.id.EntityID


class InquisitionsMapper(
    private val stepsMapper: StepsMapper,
    private val dataPointsMapper: DataPointsMapper
) : DbMapper<InquisitionDto, InquisitionDao>() {

    override val applyProps: InquisitionDao.(InquisitionDto) -> Unit = { dto ->
        workflowStep = stepsMapper.run { dto.step.toEntity() }
        className = dto.className
        dataPoint = dataPointsMapper.run { dto.dataPoint.toEntity() }
        onMetWorkflowStep = stepsMapper.run { dto.onMetWorkflowStep.toEntity() }
        executionOrder = dto.executionOrder
    }

    override fun InquisitionDto.toEntity(): InquisitionDao {
        return if (id == 0L) {
            InquisitionDao.new { applyProps(this@toEntity) }
        } else {
            InquisitionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Inquisition with id=$id not found")
        }
    }

    override fun InquisitionDao.toDto(): InquisitionDto {
        return InquisitionDto(
            id = id.value,
            step = stepsMapper.run { workflowStep.toDto() },
            className = className,
            dataPoint = dataPointsMapper.run { dataPoint.toDto() },
            onMetWorkflowStep = stepsMapper.run { onMetWorkflowStep.toDto() },
            executionOrder = executionOrder
        )
    }
}