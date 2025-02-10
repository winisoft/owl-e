package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.TestResultDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TestResultRepository : OwleLongIdRepoImpl<TestResultDao>() {

    override val entityClass: LongEntityClass<TestResultDao>
        get() = TestResultDao

    override fun getWhere(predicate: (TestResultDao) -> Boolean): List<TestResultDao> {
        return transaction {
            TestResultDao.all().filter(predicate)
        }
    }
}