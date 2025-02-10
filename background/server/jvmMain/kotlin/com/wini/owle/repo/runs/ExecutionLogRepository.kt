package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.ExecutionLogDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ExecutionLogRepository : OwleLongIdRepoImpl<ExecutionLogDao>() {

    override val entityClass: LongEntityClass<ExecutionLogDao>
        get() = ExecutionLogDao

    override fun getWhere(predicate: (ExecutionLogDao) -> Boolean): List<ExecutionLogDao> {
        return transaction {
            ExecutionLogDao.all().filter(predicate)
        }
    }
}