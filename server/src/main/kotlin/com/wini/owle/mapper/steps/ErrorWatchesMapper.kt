package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchDto
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper


class ErrorWatchesMapper(
    private val daoFactory: DaoFactory<ErrorWatchDao, Long>,
    private val stepsMapper: StepsMapper
): DbMapper<ErrorWatchDto, ErrorWatchDao>() {

    override fun toEntity(dto: ErrorWatchDto): ErrorWatchDao {
        val applyProps: ErrorWatchDao.() -> Unit = {
            step = stepsMapper.toEntity(dto.step)
            errorType = dto.errorType
            onMetStep = stepsMapper.toEntity(dto.triggerStep)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ErrorWatchDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ErrorWatch with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ErrorWatchDao): ErrorWatchDto {
        return ErrorWatchDto(
            id = entity.id.value,
            step = stepsMapper.toDto(entity.step),
            errorType = entity.errorType,
            triggerStep = stepsMapper.toDto(entity.onMetStep)
        )
    }
}