package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.step.ExecutionNotification
import com.wini.owle.entity.runs.step.ExecutionNotificationEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class ExecutionLogMapper(
    private val daoDelegate: DaoDelegate<ULong, ExecutionNotification, ExecutionNotificationEntity>,
    private val stepTestRunMapper: StepTestRunMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<ULong, ExecutionNotification, ExecutionNotificationEntity> {

    override fun toEntity(dto: ExecutionNotification): ExecutionNotificationEntity {
        return daoDelegate.findOrCreate(dto) {
            stepExecution = stepTestRunMapper.toEntity(dto.stepTestRun)
            timestamp = dateTimeConverter.fromString(dto.timestamp)
            logLevel = dto.logLevel
            message = dto.message
        }
    }

    override fun toDto(entity: ExecutionNotificationEntity): ExecutionNotification {
        return ExecutionNotification(
            id = entity.id.value,
            stepTestRun = stepTestRunMapper.toDto(entity.stepExecution),
            timestamp = dateTimeConverter.toString(entity.timestamp),
            logLevel = entity.logLevel,
            message = entity.message
        )
    }
}