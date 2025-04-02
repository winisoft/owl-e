package com.wini.owle.entity.runs

import com.wini.owle.dto.runs.LogLevelDto

enum class LogLevel {
    DEBUG, INFO, WARNING, ERROR, CRITICAL;

    companion object {
        fun fromDto(dto: LogLevelDto): LogLevel {
            return when (dto) {
                LogLevelDto.Debug -> DEBUG
                LogLevelDto.Info -> INFO
                LogLevelDto.Warning -> WARNING
                LogLevelDto.Error -> ERROR
                LogLevelDto.Critical -> CRITICAL
            }
        }
        fun LogLevel.toDto(): LogLevelDto {
            return when (this) {
                DEBUG -> LogLevelDto.Debug
                INFO -> LogLevelDto.Info
                WARNING -> LogLevelDto.Warning
                ERROR -> LogLevelDto.Error
                CRITICAL -> LogLevelDto.Critical
            }
        }
    }
}