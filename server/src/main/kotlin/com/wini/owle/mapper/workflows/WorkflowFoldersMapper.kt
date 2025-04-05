package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowFolderDto
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime


class WorkflowFoldersMapper(
    private val daoFactory: DaoFactory<WorkflowFolderDao, Long>,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<WorkflowFolderDto, WorkflowFolderDao>() {

    override fun toEntity(dto: WorkflowFolderDto): WorkflowFolderDao {
        val applyProps: WorkflowFolderDao.() -> Unit = {
            name = dto.name
            description = dto.description
            img = null
            parentFolder = null //TODO
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
        return if (dto.id == 0L) {
            daoFactory.create().apply(applyProps)
        } else {
            WorkflowFolderDao.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowFolder with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowFolderDao): WorkflowFolderDto {
        return WorkflowFolderDto(
            id = entity.id.value,
            parentFolder = null, //TODO
            name = entity.name,
            description = entity.description,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt),
            img = null
        )
    }
}