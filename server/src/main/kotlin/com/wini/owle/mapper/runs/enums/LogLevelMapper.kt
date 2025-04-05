package com.wini.owle.mapper.runs.enums

import com.wini.owle.dto.runs.LogLevelDto
import com.wini.owle.entity.runs.enums.LogLevel

class LogLevelMapper {

    fun toEntity(dto: LogLevelDto): LogLevel {
        return when (dto) {
            LogLevelDto.Debug -> LogLevel.DEBUG
            LogLevelDto.Info -> LogLevel.INFO
            LogLevelDto.Warning -> LogLevel.WARNING
            LogLevelDto.Error -> LogLevel.ERROR
            LogLevelDto.Critical -> LogLevel.CRITICAL
        }
    }

    fun toDto(entity: LogLevel): LogLevelDto {
        return when (entity) {
            LogLevel.DEBUG -> LogLevelDto.Debug
            LogLevel.INFO -> LogLevelDto.Info
            LogLevel.WARNING -> LogLevelDto.Warning
            LogLevel.ERROR -> LogLevelDto.Error
            LogLevel.CRITICAL -> LogLevelDto.Critical
        }
    }
}