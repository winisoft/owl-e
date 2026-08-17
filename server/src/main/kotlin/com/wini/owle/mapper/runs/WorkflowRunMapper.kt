package com.wini.owle.mapper.runs

import com.wini.owle.dto.runs.workflow.WorkflowRun
import com.wini.owle.entity.runs.workflow.WorkflowRunEntity
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.entity.delegate.ULongIdDaoDelegate
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.user.UsersMapper
import com.wini.owle.mapper.workflows.WorkflowMapper


open class WorkflowRunMapper(
    private val daoDelegate: ULongIdDaoDelegate<WorkflowRun, WorkflowRunEntity>,
    private val workflowMapper: WorkflowMapper,
    private val usersMapper: UsersMapper,
    private val stepTestRunMapper: StepTestRunMapper,
    private val dateTimeConverter: DateTimeConverter
): DbMapper<ULong, WorkflowRun, WorkflowRunEntity> {

    override fun toEntity(dto: WorkflowRun): WorkflowRunEntity {
        return daoDelegate.findOrCreate(dto) {
            workflow = workflowMapper.toEntity(dto.workflow)
            startedAt = dateTimeConverter.fromString(dto.startedAt)
            completedAt = dto.completedAt?.let { dateTimeConverter.fromString(it) }
            status = dto.status
        }
    }

    override fun toDto(entity: WorkflowRunEntity): WorkflowRun {
        return WorkflowRun(
            id = entity.id.value,
            workflow = workflowMapper.toDto(entity.workflow),
            startedAt = dateTimeConverter.toString(entity.startedAt),
            completedAt = entity.completedAt?.let { dateTimeConverter.toString(it) },
            status = entity.status,
        )
    }
}