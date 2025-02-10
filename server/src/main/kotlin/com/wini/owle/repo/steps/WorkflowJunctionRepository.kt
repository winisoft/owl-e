package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.WorkflowJunctionDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class WorkflowJunctionRepository : OwleLongIdRepoImpl<WorkflowJunctionDao>() {

    override val entityClass: LongEntityClass<WorkflowJunctionDao>
        get() = WorkflowJunctionDao

    override fun getWhere(predicate: (WorkflowJunctionDao) -> Boolean): List<WorkflowJunctionDao> {
        return transaction {
            WorkflowJunctionDao.all().filter(predicate)
        }
    }
}