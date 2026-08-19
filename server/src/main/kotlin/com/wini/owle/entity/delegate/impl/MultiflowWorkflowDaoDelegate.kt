package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.workflows.MultiflowWorkflow
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.workflows.MultiflowWorkflowEntity
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object MultiflowWorkflowDaoDelegate: CompositeIdDaoDelegate<
        MultiflowWorkflow,
        MultiflowWorkflowEntity,
    >(MultiflowWorkflowEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: MultiflowWorkflow): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}