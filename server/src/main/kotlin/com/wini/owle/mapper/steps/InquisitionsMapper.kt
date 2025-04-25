package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper


class InquisitionsMapper(
    private val daoFactory: DaoFactory<InquisitionEntity, Long>,
    private val stepsMapper: StepsMapper,
    private val dataPointsMapper: DataPointsMapper
): DbMapper<Inquisition, InquisitionEntity>() {

    override fun toEntity(dto: Inquisition): InquisitionEntity {
        val applyProps: InquisitionEntity.() -> Unit = {
            workflowStep = stepsMapper.toEntity(dto.step)
            className = dto.className
            dataPointEntity = dataPointsMapper.toEntity(dto.dataPoint)
            onMetWorkflowStep = stepsMapper.toEntity(dto.onMetWorkflowStep)
            executionOrder = dto.executionOrder
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            InquisitionEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Inquisition with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: InquisitionEntity): Inquisition {
        return Inquisition(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.workflowStep),
            className = entity.className,
            dataPoint = dataPointsMapper.toDto(entity.dataPointEntity),
            onMetWorkflowStep = stepsMapper.toDto(entity.onMetWorkflowStep),
            executionOrder = entity.executionOrder
        )
    }
}