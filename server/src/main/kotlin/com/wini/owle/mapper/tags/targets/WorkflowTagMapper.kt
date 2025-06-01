package com.wini.owle.mapper.tags.targets

import com.wini.owle.dto.tags.targets.WorkflowTag
import com.wini.owle.entity.delegate.DaoDelegate
import com.wini.owle.entity.tags.targets.WorkflowTagEntity
import com.wini.owle.mapper.DbMapper
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


class WorkflowTagMapper(
    private val daoDelegate: DaoDelegate<CompositeID, WorkflowTag, WorkflowTagEntity>
): DbMapper<CompositeID, WorkflowTag, WorkflowTagEntity> {

    override fun toEntity(dto: WorkflowTag): WorkflowTagEntity {
        TODO("Not yet implemented")
    }

    override fun toDto(entity: WorkflowTagEntity): WorkflowTag {
        TODO("Not yet implemented")
    }

}


