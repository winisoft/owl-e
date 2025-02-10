package com.wini.owle.mapper.runs

import com.wini.owle.dto.ExecutionLogDto
import com.wini.owle.entity.LogLevel
import com.wini.owle.entity.LogLevel.Companion.toDto
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime


class ExecutionLogMapper(
    private val stepExecutionsMapper: StepExecutionsMapper
) : DbMapper<ExecutionLogDto, ExecutionLogDao>() {

    override val applyProps: ExecutionLogDao.(ExecutionLogDto) -> Unit = { dto ->
        stepExecution = stepExecutionsMapper.run { dto.stepExecution.toEntity() }
        timestamp = LocalDateTime.parse(dto.timestamp)
        logLevel = LogLevel.fromDto(dto.logLevel)
        message = dto.message
    }

    override fun ExecutionLogDto.toEntity(): ExecutionLogDao {
        return if (id == 0L) {
            ExecutionLogDao.new { applyProps(this@toEntity) }
        } else {
            ExecutionLogDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("ExecutionLog with id=$id not found")
        }
    }

    override fun ExecutionLogDao.toDto(): ExecutionLogDto {
        return ExecutionLogDto(
            id = id.value,
            stepExecution = stepExecutionsMapper.run { stepExecution.toDto() },
            timestamp = timestamp.toJavaLocalDateTime().toString(), //TODO
            logLevel = logLevel.toDto(),
            message = message
        )
    }
}