package com.wini.owle.repo.runs

import com.wini.owle.entity.runs.TestInputDao
import com.wini.owle.repo.OwleLongIdRepoImpl
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

class TestInputRepository : OwleLongIdRepoImpl<TestInputDao>() {

    override val entityClass: LongEntityClass<TestInputDao>
        get() = TestInputDao

}