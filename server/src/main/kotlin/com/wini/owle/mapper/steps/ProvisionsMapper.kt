package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.Provision
import com.wini.owle.entity.steps.ToolParamDataPointEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.DataPointsMapper
import com.wini.owle.mapper.workflows.StepsMapper
import com.wini.owle.mapper.workflows.ToolParamsMapper


class ProvisionsMapper(
    private val daoFactory: DaoFactory<ToolParamDataPointEntity, Long>,
    private val stepsMapper: StepsMapper,
    private val toolParamsMapper: ToolParamsMapper,
    private val dataPointMapper: DataPointsMapper
): DbMapper<Provision, ToolParamDataPointEntity>() {


    override fun toEntity(dto: Provision): ToolParamDataPointEntity {
        val applyProps: ToolParamDataPointEntity.() -> Unit = {
            step = stepsMapper.toEntity(dto.step)
            toolParam = toolParamsMapper.toEntity(dto.toolParam)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ToolParamDataPointEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Provision with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ToolParamDataPointEntity): Provision {
        return Provision(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.step),
            toolParam = toolParamsMapper.toDto(entity.toolParam),
            dataPoints = entity.dataPointEntity.map { dataPointMapper.toDto(it) }
        )
    }
}