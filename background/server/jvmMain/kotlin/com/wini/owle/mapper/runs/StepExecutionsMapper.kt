package com.wini.owle.mapper.runs

import com.wini.owle.dto.StepExecutionDto
import com.wini.owle.entity.ExecutionStatus
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.StepExecutions
import com.wini.owle.entity.Steps
import com.wini.owle.entity.WorkflowExecutions
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.workflows.StepsMapper
import org.jetbrains.exposed.dao.id.EntityID


class StepExecutionsMapper(
    private val workflowExecutionsMapper: WorkflowExecutionsMapper,
    private val stepMapper: StepsMapper
) : DbMapper<StepExecutionDto, StepExecutionDao>() {

    override val applyProps: StepExecutionDao.(StepExecutionDto) -> Unit = { dto ->
        workflowExecution = workflowExecutionsMapper.run { dto.workflowExecution.toEntity() }
        step = stepMapper.run { dto.step.toEntity() }
        startedAt = dto.startedAt
        completedAt = dto.completedAt
        status = ExecutionStatus.fromDto(dto.status)
    }

    override fun StepExecutionDto.toEntity(): StepExecutionDao {
        return if (id == 0L) {
            StepExecutionDao.new { applyProps(this@toEntity) }
        } else {
            StepExecutionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("StepExecution with id=$id not found")
        }
    }

    override fun StepExecutionDao.toDto(): StepExecutionDto {
        return StepExecutionDto(
            id = id.value,
            workflowExecution = workflowExecutionsMapper.run { workflowExecution.toDto() },
            step = stepMapper.run { step.toDto() },
            startedAt = startedAt,
            completedAt = completedAt,
            status = status.toDto()
        )
    }
}