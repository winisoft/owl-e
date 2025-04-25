package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class WorkflowFoldersMapper(
    private val daoFactory: DaoFactory<WorkflowFolderEntity, Long>,
    private val dateTimeConverter: DateTimeConverter
) : DbMapper<WorkflowFolder, WorkflowFolderEntity>() {

    override fun toEntity(dto: WorkflowFolder): WorkflowFolderEntity {
        val applyProps: WorkflowFolderEntity.() -> Unit = {
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
            WorkflowFolderEntity.findById(dto.id)
                ?.apply(applyProps)
                ?: throw IllegalStateException("WorkflowFolder with id=${dto.id} not found")
        }
    }

    override fun toDto(entity: WorkflowFolderEntity): WorkflowFolder {
        return WorkflowFolder(
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