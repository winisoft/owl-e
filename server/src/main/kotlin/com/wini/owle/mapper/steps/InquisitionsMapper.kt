package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.Inquisition
import com.wini.owle.entity.steps.InquisitionEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper
import com.wini.owle.mapper.workflows.DataPointMapper
import com.wini.owle.mapper.workflows.StepsMapper


class InquisitionsMapper(
    private val daoDelegate: DaoDelegate<ULong, Inquisition, InquisitionEntity>,
    private val stepsMapper: StepsMapper,
    private val typeNodeMapper: TypeNodeMapper,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, Inquisition, InquisitionEntity> {

    override fun toEntity(dto: Inquisition): InquisitionEntity {
        return daoDelegate.findOrCreate(dto) {
            workflowStep = stepsMapper.toEntity(dto.step)
            executionOrder = dto.executionOrder
        }
    }

    override fun toDto(entity: InquisitionEntity): Inquisition {
        return Inquisition(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.workflowStep),
            executionOrder = entity.executionOrder
        )
    }
}