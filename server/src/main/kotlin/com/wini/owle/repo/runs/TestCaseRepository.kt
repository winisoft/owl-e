package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.TestCaseDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TestCaseRepository : OwleLongIdRepoImpl<TestCaseDao>() {

    override val entityClass: LongEntityClass<TestCaseDao>
        get() = TestCaseDao

    override fun getWhere(predicate: (TestCaseDao) -> Boolean): List<TestCaseDao> {
        return transaction {
            TestCaseDao.all().filter(predicate)
        }
    }
}