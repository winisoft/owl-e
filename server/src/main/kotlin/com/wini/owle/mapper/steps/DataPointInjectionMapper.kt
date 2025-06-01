package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.DataPointInjection
import com.wini.owle.entity.steps.DataPointInjectionEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointMapper


class DataPointInjectionMapper(
    private val daoDelegate: DaoDelegate<ULong, DataPointInjection, DataPointInjectionEntity>,
    private val workflowJunctionsMapper: WorkflowJunctionsMapper,
    private val dataPointMapper: DataPointMapper
): DbMapper<ULong, DataPointInjection, DataPointInjectionEntity> {

    override fun toEntity(dto: DataPointInjection): DataPointInjectionEntity {
        return daoDelegate.findOrCreate(dto) {
            junction = workflowJunctionsMapper.toEntity(dto.junction)
            sourceDataPointEntity = dataPointMapper.toEntity(dto.sourceDataPoint)
            injectedDataPointEntity = dataPointMapper.toEntity(dto.injectedDataPoint)
            name = dto.name
            description = dto.description
        }
    }

    override fun toDto(entity: DataPointInjectionEntity): DataPointInjection {
        return DataPointInjection(
            id = entity.id.value,
            junction = workflowJunctionsMapper.toDto(entity.junction),
            sourceDataPoint = dataPointMapper.toDto(entity.sourceDataPointEntity),
            injectedDataPoint = dataPointMapper.toDto(entity.injectedDataPointEntity),
            name = entity.name,
            description = entity.description
        )
    }
}