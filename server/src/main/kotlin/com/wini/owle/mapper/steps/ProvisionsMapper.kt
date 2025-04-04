package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ProvisionDto
import com.wini.owle.entity.ProvisionDataPoints // Needed for query
import com.wini.owle.entity.steps.ProvisionDao
import com.wini.owle.entity.steps.ProvisionDataPointDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.ToolParamsMapper


class ProvisionsMapper(
    private val daoFactory: DaoFactory<ProvisionDao, Long>,
    private val stepsMapper: StepsMapper,
    private val toolParamsMapper: ToolParamsMapper,
    private val dataPointMapper: DataPointsMapper
): DbMapper<ProvisionDto, ProvisionDao>() {


    override fun toEntity(dto: ProvisionDto): ProvisionDao {
        val applyProps: ProvisionDao.() -> Unit = {
            step = stepsMapper.toEntity(dto.step)
            toolParam = toolParamsMapper.toEntity(dto.toolParam)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ProvisionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Provision with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ProvisionDao): ProvisionDto {
        return ProvisionDto(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.step),
            toolParam = toolParamsMapper.toDto(entity.toolParam),
            dataPoints = entity.dataPoint.map { dataPointMapper.toDto(it) }
        )
    }
}