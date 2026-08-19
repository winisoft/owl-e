package com.wini.owle.entity.delegate.impl

import com.wini.owle.dto.workflows.WorkflowRequirement
import com.wini.owle.entity.delegate.CompositeIdDaoDelegate
import com.wini.owle.entity.workflows.WorkflowRequirementEntity
import com.wini.owle.entity.workflows.WorkflowRequirements.requiredDist
import com.wini.owle.entity.workflows.WorkflowRequirements.workflow
import com.wini.owle.entity.workflows.Workflows
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.CompositeID


object WorkflowRequirementDaoDelegate: CompositeIdDaoDelegate<
        WorkflowRequirement,
        WorkflowRequirementEntity,
        >(WorkflowRequirementEntity.Companion) {

    override fun buildId(params: Map<String, Any?>): CompositeID {
        return CompositeID.Companion {

        }
    }

    override fun extractId(dto: WorkflowRequirement): CompositeID {
        return CompositeID.Companion {

        }
    }

    override val columnsByRequestParam: Map<String, Column<out Any>> = mapOf(

    )

}