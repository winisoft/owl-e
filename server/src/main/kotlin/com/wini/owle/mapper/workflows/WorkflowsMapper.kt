package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


open class WorkflowsMapper(
    private val daoFactory: DaoFactory<WorkflowEntity, Long>,
    private val workflowFoldersMapper: WorkflowFoldersMapper,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<Workflow, WorkflowEntity>() {

    override fun toEntity(dto: Workflow): WorkflowEntity {
        val applyProps: WorkflowEntity.() -> Unit = {
            name = dto.name
            description = dto.description
            pythonVersion = dto.pythonVersion
            parentFolder = dto.parentFolder?.let { workflowFoldersMapper.toEntity(it) }
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Workflow with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowEntity): Workflow {
        return Workflow(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            pythonVersion = entity.pythonVersion,
            parentFolder = entity.parentFolder?.let { workflowFoldersMapper.toDto(it) },
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt)
        )
    }
}
