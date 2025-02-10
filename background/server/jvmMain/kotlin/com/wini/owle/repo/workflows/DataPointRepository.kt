package com.wini.owle.repo.workflows

import com.wini.owle.entity.Workflows
import com.wini.owle.entity.workflows.DataPointDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction

class DataPointRepository : OwleLongIdRepoImpl<DataPointDao>() {

    override val entityClass: LongEntityClass<DataPointDao>
        get() = DataPointDao

    fun getDataPointsOfWorkflow(workflowId: Long): List<DataPointDao> {
        return getWhere {
            it.workflow.id == EntityID(workflowId, Workflows)
        }
    }

    override fun getWhere(predicate: (DataPointDao) -> Boolean): List<DataPointDao> {
        return transaction {
            DataPointDao.all().filter(predicate)
        }
    }
}