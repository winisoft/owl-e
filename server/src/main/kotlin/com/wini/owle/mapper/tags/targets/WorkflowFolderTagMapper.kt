package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.ToolTag
import com.wini.owle.dto.tags.targets.WorkflowFolderTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.ToolTagEntity
import com.wini.owle.entity.tags.targets.WorkflowFolderTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class WorkflowFolderTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, WorkflowFolderTag, WorkflowFolderTagEntity>
): DbMapper<CompositeID, WorkflowFolderTag, WorkflowFolderTagEntity> {

    override fun toEntity(dto: WorkflowFolderTag): WorkflowFolderTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: WorkflowFolderTagEntity): WorkflowFolderTag {
        TODO("Not yet implemented")
    }

}