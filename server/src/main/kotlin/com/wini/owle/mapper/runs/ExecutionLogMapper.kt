package com.wini.owle.mapper.runs

import com.wini.owle.dto.ExecutionLogDto
import com.wini.owle.entity.LogLevel
import com.wini.owle.entity.LogLevel.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime


class ExecutionLogMapper(
    private val daoFactory: DaoFactory<ExecutionLogDao, Long>,
    private val stepExecutionsMapper: StepExecutionsMapper
) : DbMapper<ExecutionLogDto, ExecutionLogDao>() {

    override fun toEntity(dto: ExecutionLogDto): ExecutionLogDao {
        val applyProps: ExecutionLogDao.() -> Unit = {
            stepExecution = stepExecutionsMapper.toEntity(dto.stepExecution)
            timestamp = LocalDateTime.parse(dto.timestamp)
            logLevel = LogLevel.fromDto(dto.logLevel)
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
            stepExecution = stepExecutionsMapper.toDto(entity.stepExecution), // Updated call
            timestamp = entity.timestamp.toJavaLocalDateTime().toString(), // Consider standard ISO format or specific needs
            logLevel = entity.logLevel.toDto(),
            message = entity.message
        )
    }
}