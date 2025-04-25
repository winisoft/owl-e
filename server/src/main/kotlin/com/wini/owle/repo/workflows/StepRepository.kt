package com.wini.owle.repo.workflows

import com.wini.owle.entity.Phases
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.StepEntity
import com.wini.owle.repo.OwleRepoImpl
import org.jetbrains.exposed.dao.id.EntityID

class StepRepository: OwleRepoImpl<Long, StepEntity>(StepEntity) {

    fun getStepsOfWorkflow(workflowId: Long): List<StepEntity> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
        }
    }

    fun getStepsInWorkflowRoot(): List<StepEntity> {
        return getWhere {
            it.parentPhase?.id == null
        }
    }

    fun getStepsInPhase(phaseId: Long): List<StepEntity> {
        return getWhere {
            it.parentPhase?.id == EntityID(phaseId, Phases)
        }
    }

}