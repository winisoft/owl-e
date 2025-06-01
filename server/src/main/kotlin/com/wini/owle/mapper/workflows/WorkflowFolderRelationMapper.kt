package com.wini.owle.mapper.workflows

import com.wini.owle.dto.workflows.WorkflowFolder
import com.wini.owle.dto.workflows.WorkflowFolderRelation
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.workflows.WorkflowFolderEntity
import com.wini.owle.entity.workflows.WorkflowFolderRelationEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class WorkflowFolderRelationMapper(
    val daoDelegate: DaoDelegate<CompositeID, WorkflowFolderRelation, WorkflowFolderRelationEntity>,
    val workflowFolderMapper: DbMapper<ULong, WorkflowFolder, WorkflowFolderEntity>
): DbMapper<CompositeID, WorkflowFolderRelation, WorkflowFolderRelationEntity> {

    override fun toEntity(dto: WorkflowFolderRelation): WorkflowFolderRelationEntity {
        return daoDelegate.findOrCreate(dto, {})
    }

    override fun toDto(entity: WorkflowFolderRelationEntity): WorkflowFolderRelation {
        return WorkflowFolderRelation(
            parent = workflowFolderMapper.toDto(entity.parent),
            child = workflowFolderMapper.toDto(entity.child)
        )
    }

}