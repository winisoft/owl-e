package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.WorkflowExecutionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class WorkflowExecutionRepository : OwleLongIdRepoImpl<WorkflowExecutionDao>() {

    override val entityClass: LongEntityClass<WorkflowExecutionDao>
        get() = WorkflowExecutionDao

    override fun getWhere(predicate: (WorkflowExecutionDao) -> Boolean): List<WorkflowExecutionDao> {
        return transaction {
            WorkflowExecutionDao.all().filter(predicate)
        }
    }
}