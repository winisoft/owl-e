package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.PreReqInjectionDto
import com.wini.owle.entity.steps.PreReqInjectionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper


class PreReqInjectionsMapper(
    private val daoFactory: DaoFactory<PreReqInjectionDao, Long>,
    private val workflowJunctionsMapper: WorkflowJunctionsMapper,
    private val dataPointsMapper: DataPointsMapper
): DbMapper<PreReqInjectionDto, PreReqInjectionDao>() {

    override fun toEntity(dto: PreReqInjectionDto): PreReqInjectionDao {
        val applyProps: PreReqInjectionDao.() -> Unit = {
            junction = workflowJunctionsMapper.toEntity(dto.junction)
            sourceDataPoint = dataPointsMapper.toEntity(dto.sourceDataPoint)
            injectedDataPoint = dataPointsMapper.toEntity(dto.injectedDataPoint)
            name = dto.name
            description = dto.description
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            PreReqInjectionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("PreReqInjection with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: PreReqInjectionDao): PreReqInjectionDto {
        return PreReqInjectionDto(
            id = entity.id.value,
            junction = workflowJunctionsMapper.toDto(entity.junction),
            sourceDataPoint = dataPointsMapper.toDto(entity.sourceDataPoint),
            injectedDataPoint = dataPointsMapper.toDto(entity.injectedDataPoint),
            name = entity.name,
            description = entity.description
        )
    }
}