package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.step.StepTestRun
import com.wini.owle.entity.runs.step.StepTestRunEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper


class StepTestRunMapper(
    private val daoDelegate: DaoDelegate<ULong, StepTestRun, StepTestRunEntity>,
    private val stepMapper: StepsMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<ULong, StepTestRun, StepTestRunEntity> {

    override fun toEntity(dto: StepTestRun): StepTestRunEntity {
        return daoDelegate.findOrCreate(dto) {
            step = stepMapper.toEntity(dto.step)
            started = dto.started?.let { dateTimeConverter.fromString(it) }
            completed = dto.completed?.let { dateTimeConverter.fromString(it) }
            status = dto.status
        }
    }

    override fun toDto(entity: StepTestRunEntity): StepTestRun {
        return StepTestRun(
            id = entity.id.value,
            step = stepMapper.toDto(entity.step),
            startedAt = entity.started?.let { dateTimeConverter.toString(it) } ?: "",
            completedAt = entity.completed?.let { dateTimeConverter.toString(it) },
            status = entity.status
        )
    }
}