package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.Workflow
import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.entity.workflows.WorkflowEntity
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.mapper.DateTimeConverter
import com.wini.owle.mapper.DbMapper
import com.wini.owle.mapper.environs.version.PythonVersionMapper


open class WorkflowMapper(
    private val daoDelegate: DaoDelegate<ULong, Workflow, WorkflowEntity>,
    private val pythonVersionMapper: PythonVersionMapper
): DbMapper<ULong, Workflow, WorkflowEntity> {

    private val dateTimeConverter: DateTimeConverter = DateTimeConverter

    override fun toEntity(dto: Workflow): WorkflowEntity {
        return daoDelegate.findOrCreate(dto) {
            name = dto.name
            description = dto.description
            pythonVersion = pythonVersionMapper.toEntity(dto.pythonVersion)
            parentFolder = dto.parentFolder?.let {
                WorkflowFolderEntity.findById(it.id)
            }
            createdAt = dateTimeConverter.fromString(dto.createdAt)
            updatedAt = dateTimeConverter.fromString(dto.updatedAt)
        }
    }

    override fun toDto(entity: WorkflowEntity): Workflow {
        return Workflow(
            id = entity.id.value,
            name = entity.name,
            description = entity.description,
            pythonVersion = pythonVersionMapper.toDto(entity.pythonVersion),
            parentFolder = entity.parentFolder?.let {
                WorkflowFolder(
                    id = it.id.value,
                    parentFolder = null,
                    name = it.name,
                    description = it.description,
                    createdAt = dateTimeConverter.toString(it.createdAt),
                    updatedAt = dateTimeConverter.toString(it.updatedAt)
                )
            },
            createdAt = dateTimeConverter.toString(entity.createdAt),
            updatedAt = dateTimeConverter.toString(entity.updatedAt)
        )
    }
}
