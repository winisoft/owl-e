package com.wini.owle.mapper.workflows

import com.wini.owle.dto.WorkflowDto
import com.wini.owle.entity.*
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.entity.workflows.WorkflowDao
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.UserDesignedMetadataMapper
import com.wini.owle.models.metadata.UserDesignedMetadata
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.dao.id.EntityID


class WorkflowsMapper(
    private val userDesignedMetadataMapper: UserDesignedMetadataMapper,
    private val workflowFoldersMapper: WorkflowFoldersMapper
) : DbMapper<WorkflowDto, WorkflowDao>() {

    override val applyProps: WorkflowDao.(WorkflowDto) -> Unit = { dto ->
        name = dto.name
        description = dto.description
        pythonVersion = dto.pythonVersion
        parentFolder = workflowFoldersMapper.run { dto.parentFolder?.toEntity() }
        metadata = UserDesignedMetadata.mock //TODO //userDesignedMetadataMapper.run { dto.metadata }
        createdAt = LocalDateTime.parse(dto.createdAt)
        updatedAt = LocalDateTime.parse(dto.updatedAt)
    }

    override fun WorkflowDto.toEntity(): WorkflowDao {
        return if (id == 0L) {
            WorkflowDao.new { applyProps(this@toEntity) }
        } else {
            WorkflowDao.findById(id)
                ?.apply { applyProps(this@toEntity) }
                ?: throw IllegalStateException("Workflow with id=$id not found")
        }
    }

    override fun WorkflowDao.toDto(): WorkflowDto {
        return WorkflowDto(
            id = id.value,
            name = name,
            description = description,
            pythonVersion = pythonVersion,
            parentFolder = workflowFoldersMapper.run { parentFolder?.toDto() },
            metadata = userDesignedMetadataMapper.toDto(metadata),
            createdAt = DateTimeConverter.toString(createdAt),
            updatedAt = DateTimeConverter.toString(updatedAt)
        )
    }
}