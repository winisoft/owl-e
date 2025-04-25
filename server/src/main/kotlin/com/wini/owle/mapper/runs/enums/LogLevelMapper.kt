package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.enums.LogLevel
import com.wini.owle.entity.runs.enums.LogLevelEntity

class LogLevelMapper {

    fun toEntity(dto: LogLevel): LogLevelEntity {
        return when (dto) {
            LogLevel.DEBUG -> LogLevelEntity.DEBUG
            LogLevel.INFO -> LogLevelEntity.INFO
            LogLevel.WARNING -> LogLevelEntity.WARNING
            LogLevel.ERROR -> LogLevelEntity.ERROR
            LogLevel.CRITICAL -> LogLevelEntity.CRITICAL
        }
    }

    fun toDto(entity: LogLevelEntity): LogLevel {
        return when (entity) {
            LogLevelEntity.DEBUG -> LogLevel.DEBUG
            LogLevelEntity.INFO -> LogLevel.INFO
            LogLevelEntity.WARNING -> LogLevel.WARNING
            LogLevelEntity.ERROR -> LogLevel.ERROR
            LogLevelEntity.CRITICAL -> LogLevel.CRITICAL
        }
    }
}