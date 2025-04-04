package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowDto
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime


open class WorkflowsMapper(
    private val daoFactory: DaoFactory<WorkflowDao, Long>,
    private val workflowFoldersMapper: WorkflowFoldersMapper
) : DbMapper<WorkflowDto, WorkflowDao>() {

    override fun toEntity(dto: WorkflowDto): WorkflowDao {
        val applyProps: WorkflowDao.() -> Unit = {
            name = dto.name
            description = dto.description
            pythonVersion = dto.pythonVersion
            parentFolder = dto.parentFolder?.let { workflowFoldersMapper.toEntity(it) }
            createdAt = LocalDateTime.parse(dto.createdAt)
            updatedAt = LocalDateTime.parse(dto.updatedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("Workflow with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowDao): WorkflowDto {
        return WorkflowDto(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            pythonVersion = entity.pythonVersion,
            parentFolder = entity.parentFolder?.let { workflowFoldersMapper.toDto(it) },
            createdAt = DateTimeConverter.toString(entity.createdAt),
            updatedAt = DateTimeConverter.toString(entity.updatedAt)
        )
    }
}
