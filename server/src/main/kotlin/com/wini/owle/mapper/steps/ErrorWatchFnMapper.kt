package com.wini.owle.mapper.steps

import com.wini.owle.dto.steps.ErrorWatchFn
import com.wini.owle.entity.steps.ErrorWatchFnEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.typing.TypeNodeMapper


class ErrorWatchFnMapper(
    private val daoDelegate: DaoDelegate<ULong, ErrorWatchFn, ErrorWatchFnEntity>,
    private val errorWatchesMapper: ErrorWatchMapper,
    private val typeNodeMapper: TypeNodeMapper
): DbMapper<ULong, ErrorWatchFn, ErrorWatchFnEntity> {

    override fun toEntity(dto: ErrorWatchFn): ErrorWatchFnEntity {
        return daoDelegate.findOrCreate(dto) {
            errorWatchEntity = errorWatchesMapper.toEntity(dto.errorWatch)
            functionNode = typeNodeMapper.toEntity(dto.functionNode)
            position = dto.position
        }
    }

    override fun toDto(entity: ErrorWatchFnEntity): ErrorWatchFn {
        return ErrorWatchFn(
            id = entity.id.value,
            errorWatch = errorWatchesMapper.toDto(entity.errorWatchEntity),
            functionNode = typeNodeMapper.toDto(entity.functionNode),
            position = entity.position
        )
    }
}