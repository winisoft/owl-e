package com.wini.owle.mapper.runs

import com.wini.owle.dto.WorkflowExecutionDto
import com.wini.owle.entity.*
import com.wini.owle.entity.ExecutionStatus.Companion.toDto
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.runs.StepExecutionDao
import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowsMapper
import org.jetbrains.exposed.dao.id.EntityID


open class WorkflowExecutionsMapper(
    private val daoFactory: DaoFactory<WorkflowExecutionDao, Long>,
    private val workflowsMapper: WorkflowsMapper,
    private val usersMapper: UsersMapper,
    private val stepExecutionMapper: StepExecutionsMapper
): DbMapper<WorkflowExecutionDto, WorkflowExecutionDao>() {

    override fun toEntity(dto: WorkflowExecutionDto): WorkflowExecutionDao {
        val applyProps: WorkflowExecutionDao.() -> Unit = {
            workflow = workflowsMapper.toEntity(dto.workflow)
            user = usersMapper.toEntity(dto.user)
            startedAt = dto.startedAt
            completedAt = dto.completedAt
            status = ExecutionStatus.fromDto(dto.status)
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
            startedAt = entity.startedAt,
            completedAt = entity.completedAt,
            status = entity.status.toDto(),
            stepExecutions = emptyList()
        )
    }
}