package com.wini.owle.repo.workflows

import com.wini.owle.entity.Phases
import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.PhaseDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class PhaseRepository : OwleLongIdRepoImpl<PhaseDao>() {

    override val entityClass: LongEntityClass<PhaseDao>
        get() = PhaseDao

    fun getInWorkflowRoot(workflowId: Long): List<PhaseDao> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
            && it.parentPhase?.id == null
        }
    }

    fun getInParentPhase(id: Long): List<PhaseDao> {
        return getWhere {
            it.parentPhase?.id == EntityID(id, Phases)
        }
    }

    override fun getWhere(predicate: (PhaseDao) -> Boolean): List<PhaseDao> {
        return transaction {
            PhaseDao.all().filter(predicate)
        }
    }
}