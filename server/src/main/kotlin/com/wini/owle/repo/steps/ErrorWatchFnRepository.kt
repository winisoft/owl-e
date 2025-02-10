package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchFnDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ErrorWatchFnRepository : OwleLongIdRepoImpl<ErrorWatchFnDao>() {

    override val entityClass: LongEntityClass<ErrorWatchFnDao>
        get() = ErrorWatchFnDao

    override fun getWhere(predicate: (ErrorWatchFnDao) -> Boolean): List<ErrorWatchFnDao> {
        return transaction {
            ErrorWatchFnDao.all().filter(predicate)
        }
    }
}