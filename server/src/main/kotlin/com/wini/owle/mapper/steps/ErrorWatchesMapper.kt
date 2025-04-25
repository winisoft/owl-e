package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper


class ErrorWatchesMapper(
    private val daoFactory: DaoFactory<ErrorWatchEntity, Long>,
    private val stepsMapper: StepsMapper
): DbMapper<ErrorWatch, ErrorWatchEntity>() {

    override fun toEntity(dto: ErrorWatch): ErrorWatchEntity {
        val applyProps: ErrorWatchEntity.() -> Unit = {
            step = stepsMapper.toEntity(dto.step)
            errorType = dto.errorType
            onMetStep = stepsMapper.toEntity(dto.triggerStep)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ErrorWatchEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ErrorWatch with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ErrorWatchEntity): ErrorWatch {
        return ErrorWatch(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.step),
            errorType = entity.errorType,
            triggerStep = stepsMapper.toDto(entity.onMetStep)
        )
    }
}