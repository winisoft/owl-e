package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.StepExecutionDto
import com.wini.owle.entity.runs.ExecutionStatus
import com.wini.owle.entity.runs.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper


class StepExecutionsMapper(
    private val daoFactory: DaoFactory<StepExecutionDao, Long>,
    private val workflowExecutionsMapper: WorkflowExecutionsMapper,
    private val stepMapper: StepsMapper
): DbMapper<StepExecutionDto, StepExecutionDao>() {

    override fun toEntity(dto: StepExecutionDto): StepExecutionDao {
        val applyProps: StepExecutionDao.() -> Unit = {
            workflowExecution = workflowExecutionsMapper.toEntity(dto.workflowExecution)
            step = stepMapper.toEntity(dto.step)
            startedAt = dto.startedAt
            completedAt = dto.completedAt
            status = ExecutionStatus.fromDto(dto.status)
        }
        return if (dto.id == 0L)
            daoFactory.create().apply(applyProps)
        else
            StepExecutionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("StepExecution with id=${dto.id} not found")
    }

    override fun toDto(entity: StepExecutionDao): StepExecutionDto {
        return StepExecutionDto(
            id = entity.id.value,
            workflowExecution = workflowExecutionsMapper.toDto(entity.workflowExecution),
            step = stepMapper.toDto(entity.step),
            startedAt = entity.startedAt,
            completedAt = entity.completedAt,
            status = entity.status.toDto()
        )
    }
}