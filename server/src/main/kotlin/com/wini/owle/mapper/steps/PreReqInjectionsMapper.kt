package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.InitialDataPointInjector
import com.wini.owle.entity.steps.InitialDataPointInjectorEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper


class PreReqInjectionsMapper(
    private val daoFactory: DaoFactory<InitialDataPointInjectorEntity, Long>,
    private val workflowJunctionsMapper: WorkflowJunctionsMapper,
    private val dataPointsMapper: DataPointsMapper
): DbMapper<InitialDataPointInjector, InitialDataPointInjectorEntity>() {

    override fun toEntity(dto: InitialDataPointInjector): InitialDataPointInjectorEntity {
        val applyProps: InitialDataPointInjectorEntity.() -> Unit = {
            junction = workflowJunctionsMapper.toEntity(dto.junction)
            sourceDataPointEntity = dataPointsMapper.toEntity(dto.sourceDataPoint)
            injectedDataPointEntity = dataPointsMapper.toEntity(dto.injectedDataPoint)
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            InitialDataPointInjectorEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("PreReqInjection with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: InitialDataPointInjectorEntity): InitialDataPointInjector {
        return InitialDataPointInjector(
            id = entity.id.value,
            junction = workflowJunctionsMapper.toDto(entity.junction),
            sourceDataPoint = dataPointsMapper.toDto(entity.sourceDataPointEntity),
            injectedDataPoint = dataPointsMapper.toDto(entity.injectedDataPointEntity),
            name = entity.name,
            description = entity.description
        )
    }
}