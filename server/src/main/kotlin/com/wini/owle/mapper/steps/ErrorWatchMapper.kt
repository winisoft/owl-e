package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatch
import com.wini.owle.entity.steps.ErrorWatchEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper


class ErrorWatchMapper(
    private val daoDelegate: DaoDelegate<ULong, ErrorWatch, ErrorWatchEntity>,
    private val stepsMapper: StepsMapper
): DbMapper<ULong, ErrorWatch, ErrorWatchEntity> {

    override fun toEntity(dto: ErrorWatch): ErrorWatchEntity {
        return daoDelegate.findOrCreate(dto) {
            step = stepsMapper.toEntity(dto.step)
            errorType = dto.errorType
            onMetStep = stepsMapper.toEntity(dto.triggerStep)
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