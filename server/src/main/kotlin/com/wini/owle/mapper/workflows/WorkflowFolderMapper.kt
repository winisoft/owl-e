package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper


class WorkflowFolderMapper(
    private val daoDelegate: DaoDelegate<ULong, WorkflowFolder, WorkflowFolderEntity>
): DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity> {

    private val dateTimeConverter: DateTimeConverter = DateTimeConverter

    override fun toEntity(dto: WorkflowFolder): WorkflowFolderEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            description = dto.description
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
    }

    override fun toDto(entity: WorkflowFolderEntity): WorkflowFolder {
        return WorkflowFolder(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt),
            img = null
        )
    }
}