package com.wini.owle.mapper.steps

import com.wini.owle.dto.InquisitionDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.steps.InquisitionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper


class InquisitionsMapper(
    private val daoFactory: DaoFactory<InquisitionDao, Long>,
    private val stepsMapper: StepsMapper,
    private val dataPointsMapper: DataPointsMapper
): DbMapper<InquisitionDto, InquisitionDao>() {

    override fun toEntity(dto: InquisitionDto): InquisitionDao {
        val applyProps: InquisitionDao.() -> Unit = {
            workflowStep = stepsMapper.toEntity(dto.step)
            className = dto.className
            dataPoint = dataPointsMapper.toEntity(dto.dataPoint)
            onMetWorkflowStep = stepsMapper.toEntity(dto.onMetWorkflowStep)
            executionOrder = dto.executionOrder
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            InquisitionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Inquisition with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: InquisitionDao): InquisitionDto {
        return InquisitionDto(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.workflowStep),
            className = entity.className,
            dataPoint = dataPointsMapper.toDto(entity.dataPoint),
            onMetWorkflowStep = stepsMapper.toDto(entity.onMetWorkflowStep),
            executionOrder = entity.executionOrder
        )
    }
}