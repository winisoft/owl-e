package com.wini.owle.repo.workflows

import com.wini.owle.entity.Phases
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.PhaseEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class PhaseRepository: OwleRepoImpl<Long, PhaseEntity>(PhaseEntity) {

    fun getInWorkflowRoot(workflowId: Long): List<PhaseEntity> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
            && it.parentPhase?.id == null
        }
    }

    fun getInParentPhase(id: Long): List<PhaseEntity> {
        return getWhere {
            it.parentPhase?.id == EntityID(id, Phases)
        }
    }

}