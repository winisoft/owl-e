package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.workflows.WorkflowFolderRelation
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.workflows.WorkflowFolderRelationEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object WorkflowFolderRelationDaoDelegate: CompositeIdDaoDelegate<
    WorkflowFolderRelation,
    WorkflowFolderRelationEntity
>(WorkflowFolderRelationEntity) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        TODO("Not yet implemented")
    }

    override val columnsByRequestParam: Map<String, Column<out Any>>
        get() = TODO("Not yet implemented")

    override fun extractId(dto: WorkflowFolderRelation): CompositeID {
        TODO("Not yet implemented")
    }


}

