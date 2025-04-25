package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.DataPointSave
import com.wini.owle.entity.steps.DataPointSaveEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.workflows.DataPointsMapper // Assumed updated
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper // Assumed updated


class RevisionsMapper(
    private val daoFactory: DaoFactory<DataPointSaveEntity, Long>,
    private val dataPointMapper: DataPointsMapper,
    private val stepMapper: StepsMapper
): DbMapper<DataPointSave, DataPointSaveEntity>() {

    override fun toEntity(dto: DataPointSave): DataPointSaveEntity {
        val applyProps: DataPointSaveEntity.() -> Unit = {
            dataPointEntity = dataPointMapper.toEntity(dto.dataPoint)
            step = stepMapper.toEntity(dto.step)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            DataPointSaveEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Revision with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: DataPointSaveEntity): DataPointSave {
        return DataPointSave(
            id = entity.id.value,
            dataPoint = dataPointMapper.toDto(entity.dataPointEntity),
            step = stepMapper.toDto(entity.step)
        )
    }
}