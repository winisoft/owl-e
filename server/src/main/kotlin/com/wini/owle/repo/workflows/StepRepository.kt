package com.wini.owle.repo.workflows

import com.wini.owle.entity.Phases
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.StepDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class StepRepository : OwleLongIdRepoImpl<StepDao>() {

    override val entityClass: LongEntityClass<StepDao>
        get() = StepDao

    fun getStepsOfWorkflow(workflowId: Long): List<StepDao> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
        }
    }

    fun getStepsInWorkflowRoot(): List<StepDao> {
        return getWhere {
            it.parentPhase?.id == null
        }
    }

    fun getStepsInPhase(phaseId: Long): List<StepDao> {
        return getWhere {
            it.parentPhase?.id == EntityID(phaseId, Phases)
        }
    }

}