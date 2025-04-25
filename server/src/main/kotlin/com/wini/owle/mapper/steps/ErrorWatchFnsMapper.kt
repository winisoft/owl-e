package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper


class ErrorWatchFnsMapper(
    private val daoFactory: DaoFactory<ErrorWatchFnEntity, Long>,
    private val errorWatchesMapper: ErrorWatchesMapper
): DbMapper<ErrorWatchFn, ErrorWatchFnEntity>() {

    override fun toEntity(dto: ErrorWatchFn): ErrorWatchFnEntity {
        val applyProps: ErrorWatchFnEntity.() -> Unit = {
            errorWatchEntity = errorWatchesMapper.toEntity(dto.errorWatch)
            functionName = dto.functionName
            position = dto.position
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            ErrorWatchFnEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ErrorWatchFn with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: ErrorWatchFnEntity): ErrorWatchFn {
        return ErrorWatchFn(
            id = entity.id.value,
            errorWatch = errorWatchesMapper.toDto(entity.errorWatchEntity),
            functionName = entity.functionName,
            position = entity.position
        )
    }
}