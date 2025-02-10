package com.wini.owle.repo.steps

import com.wini.owle.entity.steps.ErrorWatchDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class ErrorWatchRepository : OwleLongIdRepoImpl<ErrorWatchDao>() {

    override val entityClass: LongEntityClass<ErrorWatchDao>
        get() = ErrorWatchDao

    override fun getWhere(predicate: (ErrorWatchDao) -> Boolean): List<ErrorWatchDao> {
        return transaction {
            ErrorWatchDao.all().filter(predicate)
        }
    }
}