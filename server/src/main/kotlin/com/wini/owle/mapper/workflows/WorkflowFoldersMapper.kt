package com.wini.owle.mapper.workflows

import com.wini.owle.dto.WorkflowFolderDto
import com.wini.owle.entity.WorkflowFolders
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.environs.PackageRepoDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.entity.workflows.WorkflowFolderDao
import com.wini.owle.mapper.DaoFactory
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowFoldersMapper(
    private val daoFactory: DaoFactory<WorkflowFolderDao, Long>
) : DbMapper<WorkflowFolderDto, WorkflowFolderDao>() {

    override fun toEntity(dto: WorkflowFolderDto): WorkflowFolderDao {
        val applyProps: WorkflowFolderDao.() -> Unit = {
            name = dto.name
            description = dto.description
            img = null
            parentFolder = null //TODO
            createdAt = LocalDateTime.parse(dto.createdAt)
            updatedAt = LocalDateTime.parse(dto.updatedAt)
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
            createdAt = DateTimeConverter.toString(entity.createdAt),
            updatedAt = DateTimeConverter.toString(entity.updatedAt),
            img = null
        )
    }
}