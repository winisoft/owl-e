package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.step.StepRunLogLine
import com.wini.owle.entity.runs.step.StepRunLogLineEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.LogLevelMapper


class ExecutionLogMapper(
    private val daoFactory: DaoFactory<StepRunLogLineEntity, Long>,
    private val stepExecutionsMapper: StepExecutionsMapper,
    private val logLevelMapper: LogLevelMapper,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<StepRunLogLine, StepRunLogLineEntity>() {

    override fun toEntity(dto: StepRunLogLine): StepRunLogLineEntity {
        val applyProps: StepRunLogLineEntity.() -> Unit = {
            stepExecution = stepExecutionsMapper.toEntity(dto.stepRun)
            timestamp = dateTimeConverter.fromString(dto.timestamp)
            logLevel = logLevelMapper.toEntity(dto.logLevel)
            message = dto.message
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            StepRunLogLineEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("ExecutionLog with id=${dto.id} not found")
    }

    override fun toDto(entity: StepRunLogLineEntity): StepRunLogLine {
        return StepRunLogLine(
            id = entity.id.value,
            stepRun = stepExecutionsMapper.toDto(entity.stepExecution),
            timestamp = dateTimeConverter.toString(entity.timestamp),
            logLevel = logLevelMapper.toDto(entity.logLevel),
            message = entity.message
        )
    }
}