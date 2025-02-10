package com.wini.owle.mapper.steps

import com.wini.owle.dto.ErrorWatchFnDto
import com.wini.owle.entity.ErrorWatchFns
import com.wini.owle.entity.ErrorWatches
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.dao.id.EntityID


class ErrorWatchFnsMapper(
    private val errorWatchesMapper: ErrorWatchesMapper
) : DbMapper<ErrorWatchFnDto, ErrorWatchFnDao>() {

    override val applyProps: ErrorWatchFnDao.(ErrorWatchFnDto) -> Unit = { dto ->
        errorWatch = errorWatchesMapper.run { dto.errorWatch.toEntity() }
        functionName = dto.functionName
        position = dto.position
    }

    override fun ErrorWatchFnDto.toEntity(): ErrorWatchFnDao {
        return if (id == 0L) {
            ErrorWatchFnDao.new { applyProps(this@toEntity) }
        } else {
            ErrorWatchFnDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("ErrorWatchFn with id=$id not found")
        }
    }

    override fun ErrorWatchFnDao.toDto(): ErrorWatchFnDto {
        return ErrorWatchFnDto(
            id = id.value,
            errorWatch = errorWatchesMapper.run { errorWatch.toDto() },
            functionName = functionName,
            position = position
        )
    }
}