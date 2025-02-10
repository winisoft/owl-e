package com.wini.owle.mapper.runs

import com.wini.owle.dto.WorkflowExecutionDto
import com.wini.owle.entity.*
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowExecutionsMapper(
    private val workflowsMapper: WorkflowsMapper,
    private val usersMapper: UsersMapper,
    private val stepExecutionMapper: StepExecutionsMapper
) : DbMapper<WorkflowExecutionDto, WorkflowExecutionDao>() {

    override val applyProps: WorkflowExecutionDao.(WorkflowExecutionDto) -> Unit = { dto ->
        workflow = workflowsMapper.run { dto.workflow.toEntity() }
        user = usersMapper.run { dto.user.toEntity() }
        startedAt = dto.startedAt
        completedAt = dto.completedAt
        status = ExecutionStatus.fromDto(dto.status)
    }

    override fun WorkflowExecutionDto.toEntity(): WorkflowExecutionDao {
        return if (id == 0L) {
            WorkflowExecutionDao.new { applyProps(this@toEntity) }
        } else {
            WorkflowExecutionDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("WorkflowExecution with id=$id not found")
        }
    }

    override fun WorkflowExecutionDao.toDto(): WorkflowExecutionDto {
        return WorkflowExecutionDto(
            id = id.value,
            workflow = workflowsMapper.run { workflow.toDto() },
            user = usersMapper.run { user.toDto() },
            startedAt = startedAt,
            completedAt = completedAt,
            status = status.toDto(),
            stepExecutions = StepExecutionDao
                .find { StepExecutions.workflowExecution eq id }
                .map { stepExecutionMapper.run { it.toDto() } }
        )
    }
}