package com.wini.owle.mapper.steps

import com.wini.owle.dto.ErrorWatchDto
import com.wini.owle.entity.ErrorWatchFns
import com.wini.owle.entity.ErrorWatches
import com.wini.owle.entity.Steps
import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper
import org.jetbrains.exposed.dao.id.EntityID

class ErrorWatchesMapper(
    private val stepsMapper: StepsMapper
) : DbMapper<ErrorWatchDto, ErrorWatchDao>() {

    override val applyProps: ErrorWatchDao.(ErrorWatchDto) -> Unit = { dto ->
        step = stepsMapper.run { dto.step.toEntity() }
        errorType = dto.errorType
        onMetStep = stepsMapper.run { dto.triggerStep.toEntity() }
    }

    override fun ErrorWatchDto.toEntity(): ErrorWatchDao {
        return if (id == 0L) {
            ErrorWatchDao.new { applyProps(this@toEntity) }
        } else {
            ErrorWatchDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("ErrorWatch with id=$id not found")
        }
    }

    override fun ErrorWatchDao.toDto(): ErrorWatchDto {
        return ErrorWatchDto(
            id = id.value,
            step = stepsMapper.run { step.toDto() },
            errorType = errorType,
            triggerStep = stepsMapper.run { onMetStep.toDto() }
        )
    }
}
