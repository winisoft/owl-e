package com.wini.owle.mapper.workflows

import com.wini.owle.dto.WorkflowFolderDto
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowFoldersMapper : DbMapper<WorkflowFolderDto, WorkflowFolderDao>() {

    override val applyProps: WorkflowFolderDao.(WorkflowFolderDto) -> Unit = { dto ->
        name = dto.name
        description = dto.description
        img = null
        parentFolder = null //TODO
        createdAt = LocalDateTime.parse(dto.createdAt)
        updatedAt = LocalDateTime.parse(dto.updatedAt)
    }

    override fun WorkflowFolderDto.toEntity(): WorkflowFolderDao {
        return if (id == 0L) {
            WorkflowFolderDao.new { applyProps(this@toEntity) }
        } else {
            WorkflowFolderDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("WorkflowFolder with id=$id not found")
        }
    }

    override fun WorkflowFolderDao.toDto(): WorkflowFolderDto {
        return WorkflowFolderDto(
            id = id.value,
            parentFolder = null, //TODO
            name = name,
            description = description,
            createdAt = DateTimeConverter.toString(createdAt),
            updatedAt = DateTimeConverter.toString(updatedAt),
            img = null
        )
    }
}