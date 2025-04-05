package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.WorkflowExecutionDto
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.runs.enums.ExecutionStatusMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper


open class WorkflowExecutionsMapper(
    private val daoFactory: DaoFactory<WorkflowExecutionDao, Long>,
    private val workflowsMapper: WorkflowsMapper,
    private val usersMapper: UsersMapper,
    private val stepExecutionMapper: StepExecutionsMapper,
    private val executionStatusMapper: ExecutionStatusMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<WorkflowExecutionDto, WorkflowExecutionDao>() {

    override fun toEntity(dto: WorkflowExecutionDto): WorkflowExecutionDao {
        val applyProps: WorkflowExecutionDao.() -> Unit = {
            workflow = workflowsMapper.toEntity(dto.workflow)
            user = usersMapper.toEntity(dto.user)
            startedAt = dateTimeConverter.fromString(dto.startedAt)
            completedAt = dto.completedAt?.let { dateTimeConverter.fromString(it) }
            status = executionStatusMapper.toEntity(dto.status)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowExecutionDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowExecution with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowExecutionDao): WorkflowExecutionDto {
        return WorkflowExecutionDto(
            id = entity.id.value,
            workflow = workflowsMapper.toDto(entity.workflow),
            user = usersMapper.toDto(entity.user),
            startedAt = dateTimeConverter.toString(entity.startedAt),
            completedAt = entity.completedAt?.let { dateTimeConverter.toString(it) },
            status = executionStatusMapper.toDto(entity.status),
            stepExecutions = emptyList()
        )
    }
}