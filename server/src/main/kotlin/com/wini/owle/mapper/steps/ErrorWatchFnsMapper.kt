package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchFnDto
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class ErrorWatchFnsMapper(
    private val daoFactory: DaoFactory<ErrorWatchFnDao, Long>,
    private val errorWatchesMapper: ErrorWatchesMapper
): DbMapper<ErrorWatchFnDto, ErrorWatchFnDao>() {

    override fun toEntity(dto: ErrorWatchFnDto): ErrorWatchFnDao {
        val applyProps: ErrorWatchFnDao.() -> Unit = {
            errorWatch = errorWatchesMapper.toEntity(dto.errorWatch)
            functionName = dto.functionName
            position = dto.position
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ErrorWatchFnDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ErrorWatchFn with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ErrorWatchFnDao): ErrorWatchFnDto {
        return ErrorWatchFnDto(
            id = entity.id.value,
            errorWatch = errorWatchesMapper.toDto(entity.errorWatch),
            functionName = entity.functionName,
            position = entity.position
        )
    }
}