package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.ExecutionLogDto
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.LogLevelMapper


class ExecutionLogMapper(
    private val daoFactory: DaoFactory<ExecutionLogDao, Long>,
    private val stepExecutionsMapper: StepExecutionsMapper,
    private val logLevelMapper: LogLevelMapper,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<ExecutionLogDto, ExecutionLogDao>() {

    override fun toEntity(dto: ExecutionLogDto): ExecutionLogDao {
        val applyProps: ExecutionLogDao.() -> Unit = {
            stepExecution = stepExecutionsMapper.toEntity(dto.stepExecution)
            timestamp = dateTimeConverter.fromString(dto.timestamp)
            logLevel = logLevelMapper.toEntity(dto.logLevel)
            message = dto.message
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            ExecutionLogDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ExecutionLog with id=${dto.id} not found")
    }

    override fun toDto(entity: ExecutionLogDao): ExecutionLogDto {
        return ExecutionLogDto(
            id = entity.id.value,
            stepExecution = stepExecutionsMapper.toDto(entity.stepExecution),
            timestamp = dateTimeConverter.toString(entity.timestamp),
            logLevel = logLevelMapper.toDto(entity.logLevel),
            message = entity.message
        )
    }
}